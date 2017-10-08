<CsoundSynthesizer>
<CsOptions>
-odac -dm0 -+msg_color=0
</CsOptions>
<CsInstruments>
sr = 44100
ksmps = 10
nchnls = 1
0dbfs =  1

;massign 0, 33 ;midi channel definition
;se si vul far tornare un valore da Csound ad Android predisporre un chnset
;chnset 0, "setImage" ;INIZIALIZZAZIONE DEL CANALE NECESSARIA PER FAR CAMBIARE I VALORI DEL CANALE!

/*
;-------------------------------------------------------------
instr 1 ;flute
;-------------------------------------------------------------
iGain = .6 ;global gain
kvol chnget "volume" ;volume channel definition
kvol portk kvol, .1, i(kvol) ;volume portamento
;kvol = 1
;iAmp veloc
;iAmp = iAmp/127 ;convert velocity to 0dbfs
iAmp = p5 ;midi amp substitution
;iNum notnum
iNum = p4 ;midi pitch substitution
iCps = cpsmidinn(iNum) ;notenum -> cps conversion
iCps = iCps * .890 ;frequency compensation to kjet value!

ijet init .25008 ;.18 ;a parameter controlling the air jet. Values should be positive, and about 0.3. The useful range is approximately 0.08 to 0.56.
;kjet midictrl 1, .08, .56
;printk .1, kjet

iatt = 0.1 ;time in seconds to reach full blowing pressure. 0.1 seems to correspond to reasonable playing.

idetk = 0.1 ;time in seconds taken to stop blowing. 0.1 is a smooth ending 
;kngain init 0.09055 ;amplitude of the noise component, about 0 to 0.5

kngain init 0.05118
;kngain midictrl 2, 0, .5
;printk .1, kngain

kvibf = 5.925 ;frequency of vibrato in Hertz. Suggested range is 0 to 12 
kvamp = 0.05 ;amplitude of the vibrato
kadsr madsr .02, .05, .8, .05

;ares wgflute kamp,       kfreq, kjet, iatt, idetk, kngain, kvibf, kvamp [, ifn] [, iminfreq] [, ijetrf] [, iendrf]
asig  wgflute iAmp*kadsr, iCps,  ijet, iatt, idetk, kngain, kvibf, kvamp, 1
     out asig * iGain * kvol
endin

;-------------------------------------------------------------
instr 2 ;xylophone
;-------------------------------------------------------------
iGain = .9
kvol chnget "volume" ;volume channel definition
kvol portk kvol, .1, i(kvol)
;iAmp veloc
;iAmp = iAmp/127 ;convert velocity to 0dbfs
iAmp = p5 ;midi amp substitution
;iNum notnum
iNum = p4 ;midi pitch substitution
iCps = cpsmidinn(iNum) ;notenum -> cps conversion
  ;ifreq = cpspch(p4)
  ihrd = 0.5 ;the hardness of the stick used in the strike. A range of 0 to 1 is used. 0.5 is a suitable value. 
  ipos_z = 0.561 ;where the block is hit, in the range 0 to 1.
  imp = 2 ;a table of the strike impulses. The file marmstk1.wav is a suitable function from measurements and can be loaded with a GEN01 table.
		;It is also available at ftp://ftp.cs.bath.ac.uk/pub/dream/documentation/sounds/modelling/.
  kvibf = 6.0 ;frequency of vibrato in Hertz. Suggested range is 0 to 12
  kvamp = 0.05 ;amplitude of the vibrato
  ivibfn = 3 ;shape of vibrato, usually a sine table, created by a function 
  idec = 0.6 ;time before end of note when damping is introduced
  idoubles = .1 ;randomic double strikes
  itriples = .1 ;randomic triple strikes

;NOTE: if you use GEN21 (256 21 6 5.745) instead "marmstk1.wav", you have to reduce amp (gain = *.3 about)
  a1 marimba iAmp*.3, iCps, ihrd, ipos_z, imp, kvibf, kvamp, ivibfn, idec, idoubles, itriples

  out a1 * iGain * kvol
endin

;-------------------------------------------------------------
instr 3 ;zither
;-------------------------------------------------------------
iGain = .8
kvol chnget "volume" ;volume channel definition
kvol portk kvol, .1, i(kvol) ;volume portamento
;iAmp veloc
;iAmp = iAmp/127 ;convert velocity to 0dbfs
iAmp = p5 ;midi amp substitution
;iNum notnum
iNum = p4 ;midi pitch substitution
iCps = cpsmidinn(iNum) ;notenum -> cps conversion
;or:
;iCps  = (440.0*exp(log(2.0)*((iNum)-69.0)/12.0)) ;notenum -> cps conversion
ifn  = 0 ;table number of a stored function used to initialize the cyclic decay buffer. If ifn = 0, a random sequence will be used instead. 
imeth = 6 ;method of natural decay. There are six modalities. (1 or 6 are the better for imitation of guitar or zither sound).
kEnv linenr 1, 0, .1, .01
;asig pluck kamp, kcps, icps, ifn, imeth [, iparm1] [, iparm2]
aSig  pluck iAmp, iCps, iCps, ifn, imeth,    .1,      1
aSig = aSig * kEnv
out aSig * iGain * kvol
endin

;-------------------------------------------------------------
instr 33 ;guitar
;-------------------------------------------------------------
iGain = .8
kvol chnget "volume" ;volume channel definition
kvol portk kvol, .1, i(kvol) ;volume portamento
;iAmp veloc
;iAmp = iAmp/127 ;convert velocity to 0dbfs
iAmp = p5 ;midi amp substitution
;iNum notnum
iNum = p4 ;midi pitch substitution
iCps = cpsmidinn(iNum) ;notenum -> cps conversion
;or:
;iCps  = (440.0*exp(log(2.0)*((iNum)-69.0)/12.0)) ;notenum -> cps conversion
ifn  = 0 ;table number of a stored function used to initialize the cyclic decay buffer. If ifn = 0, a random sequence will be used instead. 
imeth = 5 ;method of natural decay. There are six modalities. (1 or 6 are the better for imitation of guitar or zither sound).
kEnv linenr 1, 0, .1, .01
;asig pluck kamp, kcps, icps, ifn, imeth [, iparm1] [, iparm2]
aSig  pluck iAmp, iCps, iCps, ifn, imeth,    .5,      .5
aSig = aSig *kEnv
out aSig * iGain * kvol
endin

;-------------------------------------------------------------
instr 4 ;electric piano (Fender Rhodes)
;-------------------------------------------------------------
iGain = .8
kvol chnget "volume" ;volume channel definition
kvol portk kvol, .1, i(kvol) ;volume portamento
;iAmp veloc
;iAmp = iAmp/127 ;convert velocity to 0dbfs
iAmp = p5 ;midi amp substitution
;iNum notnum
iNum = p4 ;midi pitch substitution
iCps = cpsmidinn(iNum) ;notenum -> cps conversion
kfreq = 220
kc1 = 10 ;3 ;20 ;Mod Index 1
kc2 = 0 ;3 ;Crossfade of two outputs
kvdepth = 0.01 ;vibrator depth
kvrate = 3 ;Vibrator rate
ifn1 = 1
ifn2 = 1
ifn3 = 1
ifn4 = 4
ivfn = 1
kEnv linenr 1, 0, .1, .01
asig fmrhode iAmp*kEnv, iCps, kc1, kc2, kvdepth, kvrate, ifn1, ifn2, ifn3, ifn4, ivfn
     out asig * iGain * kvol
endin

;-------------------------------------------------------------
instr 5 ;violin
;-------------------------------------------------------------
iGain = .8
kvol chnget "volume" ;volume channel definition
kvol portk kvol, .1, i(kvol) ;volume portamento
;iAmp veloc
;iAmp = iAmp/127 ;convert velocity to 0dbfs
iAmp = p5 ;midi amp substitution
;iNum notnum
iNum = p4 ;midi pitch substitution
iCps = cpsmidinn(iNum) ;notenum -> cps conversion
kv2	= 100							;position on bow
kv1	= 30 ;line	p5, p3, p6					;bow pressure
kv4	= 1 ;line	0, p3, 7					;depth of low-frequency oscillator
kEnv    madsr .02, .05, .8, .01
asig	STKBowed iCps, iAmp, 2, kv1, 4, kv2, 11, 40, 1, kv4, 128, 100
	out	asig * kEnv * iGain * kvol
endin

*/

instr 90
turnoff2 p4, 0, p5
endin

instr 10
kvol chnget "volume" ;volume channel definition
kvol portk kvol, .1, i(kvol) ;volume portamento
kEnv    madsr .1, .05, .8, .1
asig poscil kEnv, cpsmidinn(p4)
out asig * kvol
endin


</CsInstruments>
<CsScore>
f 1 0 16384 10 1 ;sine wave
;f 2 0 256 1 "marmstk1.wav" 0 0 0
f 2 0 256 21 6 5.745 ;substitution of "marmstk1.wav", usefull for 'marimba' opcode
f 3 0 128 10 1
;f 4 0 256 1 "fwavblnk.aiff" 0 0 0
f 5 0 64 9 .5 1 0 ;used by next function
f 4 0 256 18   5 1 0 64   5 1 65 128 ;substitution of "fwavblnk.aiff", for 'fmrhode' opcode
f 0 z
</CsScore>
</CsoundSynthesizer>
