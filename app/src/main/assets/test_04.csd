<CsoundSynthesizer>
<CsOptions>
-odac -dm0 -+msg_color=0 -Fdo_la_re.mid -T
</CsOptions>
<CsInstruments>

sr = 44100
ksmps = 16
nchnls = 1
0dbfs  = 1

gisf	sfload	"flauto_dritto.sf2" ;E-mu_CreativeLab soundfont
	    sfplist	gisf
	    sfpassign 10, gisf

instr 1
iGain = .4
iAmp veloc
iAmp = iAmp/127 ;convert velocity to 0dbfs
iNum notnum
iCps = cpsmidinn(iNum) ;notenum -> cps conversion
kEnv	madsr	.05, .1, iAmp, .2
kc1 = .5
kc2 = .5
kvrate = 6
kvdpth = .1 ;line 0, p3, p6
asig fmb3 kEnv, iCps, kc1, kc2, kvdpth, kvrate
out asig * iGain
endin

instr 2
igain = .3
inum	notnum
iamp veloc
iamp = iamp/127 ;convert velocity to 0dbfs
kamp	linsegr	1, 1, 1, .1, 0
kamp	= kamp/5000						;scale amplitude
kfreq	init	1						;do not change freq from sf
asig	sfplay3m iamp, inum, kamp*iamp, kfreq, 10		;preset index = 10
	out	asig * igain
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
;f0 36.5
i10 0 300  ;metronome trigger. P3 DEVE ESSERE PIù CORTO DELLA DURATA DEL FILE MIDI, PER EVITARE PROBLEMI ALLA FINE!!!!!!! col flag -T il problema dovrebbe sparire
;col flag -T il running finisce con il midi file. se la durata dello strumento è maggiore, lo stop viene imposto dalla fine del file midi.
</CsScore>
</CsoundSynthesizer>