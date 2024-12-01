# Create a new simulation instance
set ns [new Simulator]

# Open trace file
set tracefile [open s1.tr w]
$ns trace-all $tracefile

# Open NAM animation file
set namfile [open s1.nam w]
$ns namtrace-all $namfile

# Define finish procedure to perform at the end of simulation
proc finish {} {
    global ns tracefile namfile
    $ns flush-trace

    # Dump all traces and close files
    close $tracefile
    close $namfile

    # Execute NAM animation file
    exec nam s1.nam &

    # Execute AWK file in the background
    exec awk -f s1.awk s1.tr &

    exit 0
}

# Create 3 nodes
set n0 [$ns node]
set n1 [$ns node]
set n2 [$ns node]

# Create labels
$n0 label "TCPSource"
$n2 label "TCPSink"

# Set color
$ns color 1 red

# Create links between nodes to form topology
$ns duplex-link $n0 $n1 1Mb 10ms DropTail
$ns duplex-link $n1 $n2 1Mb 10ms DropTail

# Set queue size of N packets between n1 and n2
$ns queue-limit $n1 $n2 5

# Create TCP agent and attach it to node 0
set tcp [new Agent/TCP]
$ns attach-agent $n0 $tcp

# Create TCP sink agent and attach it to node 2
set tcpsink [new Agent/TCPSink]
$ns attach-agent $n2 $tcpsink

# Create traffic: FTP on top of TCP and attach it to the TCP agent
set ftp [new Application/FTP]
$ftp attach-agent $tcp

# Connect TCP agent with TCP sink agent
$ns connect $tcp $tcpsink

# Set the color
$tcp set class_ 1

# Schedule events
$ns at 0.2 "$ftp start"
$ns at 2.5 "$ftp stop"
$ns at 3.0 "finish"

# Run the simulation
$ns run
