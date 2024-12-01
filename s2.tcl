# Create a new simulation instance
set ns [new Simulator]

# Open NAM animation file
set namfile [open s2.nam w]
$ns namtrace-all $namfile

# Open trace file
set tracefile [open s2.tr w]
$ns trace-all $tracefile

# Define finish procedure to perform at the end of simulation
proc finish {} {
    global ns namfile tracefile
    $ns flush-trace

    # Close NAM and trace files
    close $namfile
    close $tracefile

    # Execute NAM animation file
    exec nam s2.nam &

    # Execute AWK script in the background
    exec awk -f s2.awk s2.tr &

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

# Create duplex links
$ns duplex-link $n1 $n0 1Mb 10ms DropTail
$ns duplex-link $n2 $n0 1Mb 10ms DropTail
$ns duplex-link $n3 $n0 1.75Mb 20ms DropTail
$ns duplex-link $n4 $n0 1Mb 10ms DropTail
$ns duplex-link $n5 $n0 1Mb 10ms DropTail
$ns duplex-link $n6 $n0 1Mb 10ms DropTail

# Set link orientations
$ns duplex-link-op $n0 $n1 orient right
$ns duplex-link-op $n0 $n2 orient left
$ns duplex-link-op $n0 $n3 orient right-up
$ns duplex-link-op $n0 $n4 orient right-down
$ns duplex-link-op $n0 $n5 orient left-up
$ns duplex-link-op $n0 $n6 orient left-down

# Ping agent procedure
Agent/Ping instproc recv {from rtt} {
    $self instvar node
    puts "node [$node id] received ping answer from $from with round-trip-time $rtt ms"
}

# Create Ping agents
set p1 [new Agent/Ping]
set p2 [new Agent/Ping]
set p3 [new Agent/Ping]
set p4 [new Agent/Ping]
set p5 [new Agent/Ping]
set p6 [new Agent/Ping]

# Attach Ping agents to nodes
$ns attach-agent $n1 $p1
$ns attach-agent $n2 $p2
$ns attach-agent $n3 $p3
$ns attach-agent $n4 $p4
$ns attach-agent $n5 $p5
$ns attach-agent $n6 $p6

# Set queue limits
$ns queue-limit $n0 $n4 1
$ns queue-limit $n0 $n5 2
$ns queue-limit $n0 $n6 2

# Connect Ping agents
$ns connect $p1 $p4
$ns connect $p2 $p5
$ns connect $p3 $p6

# Schedule events
$ns at 0.2 "$p1 send"
$ns at 0.4 "$p2 send"
$ns at 0.6 "$p3 send"
$ns at 1.0 "$p4 send"
$ns at 1.2 "$p5 send"
$ns at 1.4 "$p6 send"
$ns at 2.0 "finish"

# Run the simulation
$ns run
