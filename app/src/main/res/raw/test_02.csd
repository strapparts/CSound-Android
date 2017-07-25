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

;ktrig1 trigger ktrig, .5, 1
;ktrig0 trigger ktrig, .5, 0

ktrigSwitch changed ktrig ;emette '1' ogni volta che 'ktrig' cambia stato (da '0' a '1' o voceversa)
ckgoto ktrigSwitch == 1, changeIt ; va a 'changeIt' ogni volta che ktrig emette '1'
kgoto jump ;vai a jump (salta la reinizializzazione, se non si verifica la condizione della riga precedente)
reset:
ichx = (i(ktrig) == 1 ? 999 : 99) ;se 'ktrig' = 1 allora 'ichx' = 999, altrimenti 99
ifrq = (i(ktrig) == 1 ? 440/1.25 : 440) ;se 'ktrig' = 1 allora 'ifrq' = 660, altrimenti 440
chnset ichx, "setImage"
rireturn ;termina il passo di reinizializzazione
changeIt:
reinit reset ;reinizializza a partire da 'reset'
jump:


;if ktrig == 1 then

;kchx = 999
;kfrq = 660 ;440 / 1.25 ;a thirth major down
;endif


;chnset kchx, "setImage"
;printk .1, ktrig
;printk .1, kchx
kfrq portk ifrq, .05, ifrq
kenv linseg 0, .1, .5, p3-.2, .5, .1, 0
asig poscil kenv*kvol, kfrq
out asig
endin
;ktrig invalue "button-channel-1"
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
