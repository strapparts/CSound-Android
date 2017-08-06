<CsoundSynthesizer>
<CsOptions>
-odac -B2048 -b512 -dm0 -+msg_color=0
;-odac -M0 -B2800 -b400 -+raw_controller_mode=1
;-o dac -d -B2048 -b512
</CsOptions>
<CsInstruments>
sr = 44100
ksmps = 64
nchnls = 1
0dbfs = 1.0
gkcf init 0

instr 1
;kplay chnget "buttonPlay"
;kstop chnget "buttonStop"
kvol chnget "volume"
kvol portk kvol, .1, i(kvol)
idur = abs(p3)
iamp = 1
igain = .1
itiv tival
i1 = -1
tigoto slur
i1 = 0
aatt linenr iamp, .5, .1, .2
slur:
if itiv ==0 kgoto note
aslur linsegr 0, .2, iamp, .5, 0
aatt = aatt + aslur
note:
asig oscili aatt, gkcf, 1, i1
out asig*igain*kvol
endin

instr 2
igain = .01
kenv linseg 0, 1, igain, p3-2, igain, 1, 0
kvol chnget "volume2"
kvol portk kvol, .1, i(kvol)
gkcf randomi 500, 900, .2
anoise rand .05
asig reson anoise, gkcf, 100
out asig*kenv*kvol
endin
</CsInstruments>
<CsScore>
f1 0 16384 10 1
i2 0 3600
</CsScore>
</CsoundSynthesizer>