Launch syntax: (see report/GUO_PETIT_Report.pdf for more details)

Longest common subsequence:
java LCS string1 string2

Simple global alignment:
java BasicAlignment string1 string2

Global alignment w/ substitution matrices:
java Alignment string1 string2

Global alignment w/ substitution matrices & affine penalty:
java AffinePenalty string1 string2 OPENING_GAP_PENALTY INCREASING_GAP_PENALTY

Local alignment:
java LocalAlignment string1 string2 OPENING_GAP_PENALTY INCREASING_GAP_PENALTY

BLAST:
java BLAST string1 string2 th thl

2D H-P protein folding:
java OptimalFolding protein

