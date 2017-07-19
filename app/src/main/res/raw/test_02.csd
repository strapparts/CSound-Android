<CsoundSynthesizer>
<CsOptions>
-odac -dm0 -+msg_color=0
;-o dac -d -B2048 -b512
</CsOptions>
<CsInstruments>

sr = 44100
ksmps = 64
nchnls = 1
0dbfs = 1.0

;stabilisce il valore iniziale che compare nell'immagine
;gkchx = 0
chnset 0, "setImage" ;INIZIALIZZAZIONE NECESSARIA PER FAR CAMBIARE I VALORI DEL CANALE
instr 1
kfrq = 440
kchx = 99
ktrig chnget "button-channel-1"
kvol chnget "volume"
kvol portk kvol, .1, i(kvol)
;ktrig chnget "newitem"

if ktrig == 1 then
;if ktrig > 0 then
kchx = 999
kfrq = 440 / 1.25 ;a thirth major down
endif

chnset kchx, "setImage"
printk .1, ktrig
printk .1, kchx
kfrq portk kfrq, .05, i(kfrq)
kenv linseg 0, .1, .5, p3-.2, .5, .1, 0
asig poscil kenv*kvol, kfrq
out asig
endin

instr 2
iamp = .5
icoda = .1
xtratim icoda
kenv linsegr 0, .1, iamp, p3-.1, iamp, icoda, 0
asig buzz kenv, 660, 5, 1
out asig
endin
/*
instr 3 ;GEN01 instr
ifn	    =  2   ; table number
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
out	      atab
endin
*/

</CsInstruments>
<CsScore>
f1 0 16384 10 1
;f2 0 65536 1 "f4_pianoforte.wav" 0 0 2
i1 0 3600
</CsScore>
</CsoundSynthesizer>