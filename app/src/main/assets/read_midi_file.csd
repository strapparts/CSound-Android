<CsoundSynthesizer>
<CsOptions>
-odac -dm0 -+msg_color=0 -Fdo_la_re.mid
</CsOptions>
<CsInstruments>

sr = 44100
ksmps = 16
nchnls = 1
0dbfs  = 1

;massign 2, 5

instr 1 ;electric piano
;midiprogramchange 74
iGain = .1
iAmp veloc
iAmp = iAmp/127 ;convert velocity to 0dbfs
iNum notnum
iCps = cpsmidinn(iNum) ;notenum -> cps conversion
;or:
;iCps  = (440.0*exp(log(2.0)*((iNum)-69.0)/12.0)) ;notenum -> cps conversion
kEnv	madsr	.05, .1, iAmp, .2

kv1	madsr	.05, .1, iAmp, .2			;(FM) Modulator Index One
kv5	=	50					;ADSR 2 and 4 target
;asig   STKRhodey ifrq, iamp,     [kmod, kv1[, kcross, kv2[, klfo, kv3[, klfodepth, kv4[, kadsr, kv5]]]]]
;aSig	STKRhodey iCps, iAmp, 2,   kv1,  4,      10,   11,   100,  1,         3,    128,    kv5
aSig poscil iAmp, iCps, 1 ;sostituisce riga precedente!!!!!!!!!!!!!!!

aSig	= aSig * kEnv * iGain
out aSig
endin

instr 2 ;flute
iVel veloc
iAmp = iVel/127
iGain = .2
iNot notnum
iCps = cpsmidinn(iNot)
kEnv madsr .5, .2, .6, .1

kv1	line	80, 3, 80	;jet delay
kv4	line	0, 3, 80	;vibrato depth

;asig	STKFlute ifrq, iamp,[kjet, kv1[, knoise, kv2[, klfo, kv3[, klfodepth, kv4[, kbreath, kv5]]]]]
;aSig	STKFlute iCps, iAmp, 2,    kv1 ,   4,    100,   11,  100,    1,       kv4,  128,     90
aSig poscil iAmp, iCps, 1 ;sostituisce riga precedente!!!!!!!!!!!!!!!!!!!


aSig	= aSig * kEnv * iGain
out aSig
endin

instr 3 ;guitar
iGain = .1
iAmp veloc
iAmp = iAmp/127 ;convert velocity to 0dbfs
iNum notnum
iCps = cpsmidinn(iNum) ;notenum -> cps conversion
;or:
;iCps  = (440.0*exp(log(2.0)*((iNum)-69.0)/12.0)) ;notenum -> cps conversion
ifn  = 0
imeth = 6
kEnv	madsr	.05, .1, iAmp, .2
;asig pluck kamp, kcps, icps, ifn, imeth [, iparm1] [, iparm2]
aSig  pluck iAmp, iCps, iCps, ifn, imeth,    .1,      10
aSig = aSig * kEnv * iGain
out aSig
endin

instr 4 ;marimba - IT NEEDS TWO TABLES!!!!!!!!!
iGain = .1
iAmp veloc
iAmp = iAmp/127 ;convert velocity to 0dbfs
iNum notnum
iCps = cpsmidinn(iNum) ;notenum -> cps conversion
;or:
;iCps  = (440.0*exp(log(2.0)*((iNum)-69.0)/12.0)) ;notenum -> cps conversion

ihrd = 0.1 ;the hardness of the stick used in the strike. A range of 0 to 1 is used. 0.5 is a suitable value.
iposit = 0.561 ;ipos = .01 ;where the block is hit, in the range 0 to 1.
imp = 3 ;a table of the strike impulses. The file marmstk1.wav is a suitable function
kvibf = 6.0 ;kvibf -- frequency of vibrato in Hertz. Suggested range is 0 to 12
kvamp = 0.05 ;kvamp -- amplitude of the vibrato
ivibfn = 4 ;ivfn -- shape of vibrato, usually a sine table, created by a function
idec = .6 ;idec -- time before end of note when damping is introduced
idoubles = .01 ;idoubles (optional) -- percentage of double strikes. Default is 40%.
itriples = .01 ;itriples (optional) -- percentage of triple strikes. Default is 20%.

;asig marimba kamp, kfrq, ihrd, ipos, imp, kvibf, kvamp, ivibfn, idec [, idoubles] [, itriples]
aSig marimba iAmp, iCps, ihrd, iposit, imp, kvibf, kvamp, ivibfn, idec, idoubles, itriples
aSig = aSig * iGain
out aSig
endin

instr 5 ;vibes - IT NEEDS TWO TABLES!!!!!!!!!
iGain = .1
iAmp veloc
iAmp = iAmp/127 ;convert velocity to 0dbfs
iNum notnum
iCps = cpsmidinn(iNum) ;notenum -> cps conversion
;or:
;iCps  = (440.0*exp(log(2.0)*((iNum)-69.0)/12.0)) ;notenum -> cps conversion
kEnv	madsr	.05, .1, iAmp, .2
; kamp = 20000
  ; kfreq = 440
  ; ihrd = 0.5
  ; ipos = p4
  ; imp = 1
  ; kvibf = 6.0
  ; kvamp = 0.05
  ; ivibfn = 2
  ; idec = 0.1
;ares  vibes    kamp, kfreq, ihrd, ipos, imp, kvibf, kvamp, ivibfn, idec
aSig	vibes	iAmp, iCps,  .5,   .561,  3,   6.0,   0.05,   4,     .1
aSig = aSig * kEnv * iGain
out aSig
endin


instr 10 ;metronome trigger
ktempo miditempo
;prints "miditempo = %d\\n", ktempo
;printk .1, ktempo
kbeat metro ktempo/60
;printk2 kbeat
;           trig, mintim, maxnum,  instrnum, when, dur
schedkwhen kbeat,   0,      0,       11,       0,   .3
endin

instr 11 ;metronome sound
iCps = 200
iAmp = .5
   ihrd = 0.5
   iposit = .1 ;0.561
   kvibf = 6.0
   kvamp = 0.3
aSig gogobel iAmp, iCps, ihrd, iposit, 3, 6.0, 0.3, 4
out aSig
endin

</CsInstruments>
<CsScore>
f1 0 16384 10 1
f2 0 16384 10 1 1 1 .8 .6 .4 .2 ;PROVVISORIO PER POSCIL al posto di ftk...
; Table #1, the "marmstk1.wav" audio file.
;f3 0 256 1 "marmstk1.wav" 0 0 0 ;wave file for marimba, vibes, gogobel!!!!!!
;f3 0 512 1 "marmstk1_44100.wav" 0 0 0 ;wave file for marimba, vibes, gogobel!!!!!! sostituita tabella precedente per vedere se la frequenza di campionamento diversa della tabella crea problemi
f3 0 256 7 1 64 0 192 0 ;sostituita tabella precedente per verificare se il problema dipende dalla lettura del file .wav
; Table #2, a sine wave for the vibrato.
f4 0 128 10 1 ;table for marimba, vibes, gogobel!!!!!!
f0 36.5
i10 0 34  ;metronome trigger. P3 DEVE ESSERE PIù CORTO DELLA DURATA DEL FILE MIDI, PER EVITARE PROBLEMI ALLA FINE!!!!!!!

</CsScore>
</CsoundSynthesizer>