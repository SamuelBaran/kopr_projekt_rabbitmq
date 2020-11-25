# **O projekte**

projekt vznikol ako výstup predmetu KOPR 

spracoval som variantu IV. pomocou RabbitMQ broker-a

# **Zadanie – distribuovaný program**
Firma Paytel s.r.o. vyrába systém na peer-to-peer mobilné platby, teda rýchly mechanizmus prevodu peňazí medzi mobilnými telefónmi.

Každý účastník má svoj mobilný telefón s kreditom, ktorý môže použiť na platbu inému účastníkovi.

### Systém má podporovať nasledovné služby:

**1. platba inému účastníkovi.** Platca odošle na iné telefónne číslo sumu, účel platby. Eviduje sa odosielateľ a dátum a čas platby.

**2. dobitie kreditu.** Z externého systému sa dobije účastníkovi kredit. Eviduje sa dátum a čas platby, suma a lokácia, na ktorej sa kredit dobil.

**3. potvrdenie prichádzajúcej platby.** Účastník môže ľubovoľnú prichádzajúcu platbu odmietnuť, čím vráti peniaze na účet odosielateľa.

**4.** získať **prehľad transakcií** používateľa a umožniť filtrovanie:
1. odchádzajúce alebo prichádzajúce transakcie
2. v danom časovom okne – napr. medzi 1. januárom 2020 od 15:20 do 7. januára 202 do 24:00
3. v danom čase zistiť aktuálny stav peňazí na účte konkrétneho používateľa

**5.** identifikovať **podozrivé platby** v reálnom čase. Pre jednoduchosť je platba podozrivá, ak je nad 5000 eur. Takéto platby okamžite zaznamenávajte vhodným spôsobom, aby sa jednoducho dalo v budúcnosti zrealizovať notifikovanie pomocou SMS, mailu a podobne.

### Technické požiadavky
Implementáciu databázy — ak to považujete za potrebné — zvoľte podľa vlastného uváženia. Nezabúdajte na to, že ku databáze budú pristupovať viacerí klienti naraz.  I v distribuovanom softvéri platia pravidlá pre konkurentné programovanie!

## Varianty zadania
Vyberte si jeden z variantov zadania a implementujte ho úplne. Zadanie, ktoré nespĺňa všetky požiadavky vo vybranej možnosti, bude zamietnuté. Pri zadaní sa hodnotí kvalita a spôsob implementácie.

**I. SOAP webservice (od kódu k WSDL)**

Implementujte SOAP webservice v štýle document/literal. Zverejnite WSDL so službou a vytvorte k nej klienta v ľubovoľnej technológii, pričom odporúčame použiť JAX-WS 2.0. Demonštrujte funkčnosť servera i klienta — odporúčaná forma sú unit testy.

Vzhľadom na špecifiká klient-server architektúry môžete zlúčiť body 1 a 3 do jedného – server nech odpovie úspechom alebo zamietnutím platby, čo preukážte v demonštrácii.

**II. SOAP webservice (od WSDL ku kódu)**

Vytvorte ručne WSDL, ktoré popisuje ľubovoľnú jednu z vyššie uvedenú operácií. Na základe tohto WSDL implementujte serverovskú a klientskú časť pre túto operáciu v ľubovoľnej technológii. Použite ľubovoľný štýl (i keď odporúčame document/literal.) Demonštrujte funkčnosť servera i klienta — odporúčaná forma sú unit testy.

**III. Akka aktor**

Implementujte zadanie, ktoré podporuje len nasledovné vlastnosti:

1. platba inému účastníkovi. 
2. dobitie kreditu.
3. podozrivé platby

Prehľad transakcií a potvrdenie platieb neimplementujte.

Dodajte kód, ktorý realizuje platby a dobíjanie a kód pre evidenciu podozrivých platieb. Rátajte s tým, že monitorovanie podozrivých platieb je nezávislé od samotného doručenia peňazí – peniaze sa nesmú stratiť!

**IV. Broker RabbitMQ alebo Kafka**
Vyberte si jeden broker — buď RabbitMQ alebo Kafka — a implementujte zadanie, ktoré podporuje len nasledovné vlastnosti:

1. platba inému účastníkovi. 
2. dobitie kreditu.
3. podozrivé platby

Prehľad transakcií a potvrdenie platieb neimplementujte.

Nezabúdajte, že platcov je veľké množstvo, rovnako ako prijímateľov dát.

Dodajte kód, ktorý realizuje platby a dobíjanie a kód pre evidenciu podozrivých platieb. Rátajte s tým, že monitorovanie podozrivých platieb je nezávislé od samotného doručenia peňazí – peniaze sa nesmú stratiť!