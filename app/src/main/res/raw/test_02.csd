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
chnset 0, "setImage" ;INIZIALIZZAZIONI NECESSARIE PER FAR CAMBIARE I VALORI DEL CANALE
chnset 0, "setImage2"

instr 1 ;modalità "switch button"
;kfrq = 440
;kchx = 99

ichx init 99
ifrq init 440
ktrig chnget "button-channel-1"
;ktrig metro 1 ;PROVVISORIO!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
kvol chnget "volume"
kvol portk kvol, .1, i(kvol)
ckgoto ktrig == 1, changeIt
kgoto jump ;vai a jump (salta la reinizializzazione, se non si verifica la condizione della riga precedente)
reset:
;ktrig = ktrig == 0 ? 1 : 0 ;ad ogni init pass assegna alternativamente a 'ktrig' i valori '0' e '1'
ichx = (ichx == 99 ? 999 : 99) ;se 'ktrig' = 1 allora 'ichx' = 999, altrimenti 99
ifrq = (ifrq == 440 ? 440/1.25 : 440) ;se 'ktrig' = 1 allora 'ifrq' = 660, altrimenti 440
;chnset ichx, "setImage"
chnset ichx, "setImage"
rireturn ;termina il passo di reinizializzazione
changeIt:
reinit reset ;reinizializza a partire da 'reset'
jump:
ichx = ichx
printk2 ichx
;chnset ichx, "setImage"
kfrq portk ifrq, .05, ifrq
kenv linseg 0, .1, .5, p3-.2, .5, .1, 0
asig poscil kenv*kvol, ifrq
out asig * .5
endin

instr 2 ;modalità "push button"
;kfrq = 527.5
;kchx = 99
ktrig chnget "button-channel-2"
;ktrig metro 1 ;PROVVISORIO!!! PER SIMULARE CIò CHE ESCE DAL CANALE, PROVVISORIO!!!
kvol chnget "volume"
kvol portk kvol, .1, i(kvol)
ktrigSwitch changed ktrig ;emette '1' ogni volta che 'ktrig' cambia stato (da '0' a '1' o voceversa)
ckgoto ktrigSwitch == 1, changeIt ; va a 'changeIt' ogni volta che ktrig emette '1'
kgoto jump ;vai a jump (salta la reinizializzazione, se non si verifica la condizione della riga precedente)
reset:
ichx = (i(ktrig) == 1 ? 999 : 99) ;se 'ktrig' = 1 allora 'ichx' = 999, altrimenti 99
ifrq = (i(ktrig) == 1 ? 527.5/1.33 : 527.5) ;se 'ktrig' = 1 allora 'ifrq' = 660, altrimenti 440
chnset ichx, "setImage2"
rireturn ;termina il passo di reinizializzazione
changeIt:
reinit reset ;reinizializza a partire da 'reset'
jump:

kfrq portk ifrq, .05, ifrq
kenv linseg 0, .1, .5, p3-.2, .5, .1, 0
asig poscil kenv*kvol, ifrq
out asig * .5
endin

</CsInstruments>
<CsScore>
i1 0 3600
i2 0 3600
</CsScore>
</CsoundSynthesizer>