# EksamensProjekt
Lavet af : CodeyFlex, MarcKlesiewicz og Yokarak
demonstrativ video for appen : https://discordapp.com/channels/709344809311273010/709344809311273013/720784775845118012
Det færdige produkt ligger i under Release

Case: 
Først års Eksamens Projekt

EksamensProjekt er vores FørsteårsProjekt på Sjælland erhvervsakademi, Zealand
hvor vi skal ud fra en software virksomheds syn lave et produkt for Frederiksberg Sportsklinik er en privat klinik på Frederiksberg, som beskæftiger sig med fysioterapi og akupunktur.

Appen, som skal udvikles skal facilitere en form for klubkoncept:

Kommunikation mellem behandler og patient.

Kommunikation via beskeder

Online booking (Complimenta, login og journalsystem)

Træning

Deling af træningsprogram (udarbejdet i Exorlive)

Træningsvideoer, begrænset til det konkrete program

Tracking

Feedback til øvelser

Dørkode (Dansikring)

Hertil kan man overveje at udvikle en matchende behandler-app (gerne til desktop.)

--------------------------------------------------------------------------------------------------------------------------------------
Arkitektur :
Med vores app har vi prøvet at følge Grasp 9 principper så godt som vi har kunne.
med nogle af klasserne eller metoderne har vi fået det gjort til at følge MVVM arkitekturen.
Vi har også prøvet at fremvise en MVP arkitektur.

Vores Database valg blev Real time Database for vores Chat Use case og Bruger data
og Cloud Firestore for vores Booking Use case

Technical: 
Vores App er det kun lavet til Android telefoner
minimum SDKVersion = 14
target SDKVersion = 29

Vi bruger følgende libraries : 
Jaredrummler's Material spinner 1.3.1
Kofigyan's StateProgressbar 1.0.0
Bumptech's Glode 4.11.0

Firebase auth 19.3.1
Firebase Database 19.3.0
Firebase Firestore 21.4.3

License
Copyright (C) 2020 FRB-SportsApp

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
