# Create a new simulation instance
set ns [new Simulator]

# Open NAM animation file
set namfile [open s3.nam w]
$ns namtrace-all $namfile

# Open trace file
set tracefile [open s3.tr w]
$ns trace-all $tracefile

# Define finish procedure to perform at the end of simulation
proc finish {} {
    global ns namfile tracefile
    $ns flush-trace

    # Close NAM and trace files
    close $namfile
    close $tracefile

    # Execute NAM animation file
    exec nam s3.nam &
    exit 0
}

# Create nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]
set n3 [$ns node]
set n4 [$ns node]
set n5 [$ns node]
set n6 [$ns node]
set n7 [$ns node]
set n8 [$ns node]

# Set node colors and shapes
$ns color 1 Blue
$ns color 2 Red
$n7 shape box
$n7 color Blue
$n8 shape hexagon
$n8 color Red

# Create duplex links
$ns duplex-link $n1 $n0 2Mb 10ms DropTail
$ns duplex-link $n2 $n0 2Mb 10ms DropTail
$ns duplex-link $n0 $n3 1Mb 20ms DropTail

# Create LAN
$ns make-lan "$n3 $n4 $n5 $n6 $n7 $n8" 512Kb 40ms LL Queue/DropTail Mac/802_3

# Set link orientations
$ns duplex-link-op $n1 $n0 orient right-down
$ns duplex-link-op $n2 $n0 orient right-up
$ns duplex-link-op $n0 $n3 orient right

# Set queue limits
$ns queue-limit $n0 $n3 20

# Configure TCP Vegas
set tcp1 [new Agent/TCP/Vegas]
$ns attach-agent $n1 $tcp1
set sink1 [new Agent/TCPSink]
$ns attach-agent $n7 $sink1
set ftp1 [new Application/FTP]
$ftp1 attach-agent $tcp1
$ns connect $tcp1 $sink1
$tcp1 set class_ 1
$tcp1 set packetSize_ 55
set tfile1 [open cwndl.tr w]
$tcp1 attach $tfile1
$tcp1 trace cwnd

# Configure TCP Reno
set tcp2 [new Agent/TCP/Reno]
$ns attach-agent $n2 $tcp2
set sink2 [new Agent/TCPSink]
$ns attach-agent $n8 $sink2
set ftp2 [new Application/FTP]
$ftp2 attach-agent $tcp2
$ns connect $tcp2 $sink2
$tcp2 set class_ 2
$tcp2 set packetSize_ 55
set tfile2 [open cwnd2.tr w]
$tcp2 attach $tfile2
$tcp2 trace cwnd

# Schedule events
$ns at 0.5 "$ftp1 start"
$ns at 1.0 "$ftp2 start"
$ns at 5.0 "$ftp2 stop"
$ns at 5.0 "$ftp1 stop"
$ns at 5.5 "finish"

# Run the simulation
$ns run
