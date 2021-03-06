<CsoundSynthesizer>
<CsOptions>
-odac -dm0 -+msg_color=0 ;buffer troppo basso!!
</CsOptions>
<CsInstruments>

sr = 44100
ksmps = 128
nchnls = 2
0dbfs = 1.0

instr 1 ;GEN01 instr
ifn	    =  p4   ; table number
ilen	    =  nsamp(ifn)   ; return actual number of samples in table
itrns	    =  1   ; no transposition
ilps	    =  0   ; loop starts at index 0
ilpe	    =  ilen ; ends at value returned by nsamp above
imode	    =  0    ; 0=no loop; 1=forward loop; 2=backward loop; 3=forward & backward loop
istrt	    =  0  ; commence playback at index 10000 samples
; lphasor provides index into f1
alphs	    lphasor   itrns, ilps, ilpe, imode, istrt
atab	    tablei    alphs, ifn
; gain signal
atab	    =  atab * .3
outs	      atab, atab
endin
</CsInstruments>
<CsScore>
f2 0 65536 1 "f4_pianoforte.wav" 0 0 2
f3 0 65536 1 "g4_pianoforte.wav" 0 0 2
f4 0 65536 1 "a4_pianoforte.wav" 0 0 2
f5 0 65536 1 "c5_pianoforte.wav" 0 0 2
f0 z
;i1 0 .7 2
;i1 + . 3
</CsScore>
</CsoundSynthesizer>

