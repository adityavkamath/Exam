BEGIN {
    # No initialization code needed
}

{
    if ($6 == "cwnd_") {
        printf("%f\t%f\n", $1, $7);
    }
}

END {
    # No finalization code needed
}
