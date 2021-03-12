## Hold C3 - Chat Server/Client
 Authors: Peter, René, Simon, Wehba
 
 Design beskrivelse:
 En simpel chat server med en hardcoded bruger liste. Projektet er opdelt i to packages; en client 
 og en server del. Serveren består af en ChatServer klasse, der lytter til indkommende klient kald.
 Der startes en ny tråd for hver forbundne klient, hvor der instantieres en ClientHandler til
 at håndtere netop denne klient. Clienthandler opfylder kravsspecikationerne til nævnte protokoller:<br>
 Client; CONNECT, SEND, CLOSE
 <br>Server; ONLINE, MESSAGE, CLOSE
<br><br>
 Hvem gjorde hvad:<br>
 Alle medlemmer har deltaget fuld tid og kommet med input, samt deltaget i problemhåndtering.
 Peter har styret slagets gang og har kodet det meste fra hans workstation, hvilket kan ses ud fra
 git log.
<br><br>
 Acceptance test:<br>
 Vi har udført test af klient og server med Hold C1 og som udgangspunkt var testen tilfredsstillende.
  - Vi fandt problemer med håndtering af tokens.
  - En klient får ikke en CLOSE#1 når vedkommende prøver at logge på en bruger som allerede er connected.
  - Vi fandt også ud af, at vi ikke fjerner brugere fra vores Vector (ar) liste når de “disconnecter”.
<br>
<p>Ovenstående fejl er rettet.</p>
<br>
 Log: https://docs.google.com/document/d/1pEx3qP2eeY3Aa7B7DvJRa9dOMNV6M9T4a_M7MNBPyss/edit?usp=sharing
 
<br><br><br><hr>

## Quick Start Project for the Chat - Server

Simple Maven Project which can be used for the Chat-CA 

Using this project as your start code will make deploying your server (the jar-file) to Digital Ocean a "no brainer" if you follow the instructions given here

https://docs.google.com/document/d/1aE1MlsTAAYksCPpI4YZu-I_uLYqZssoobsmA-GHmiHk/edit?usp=sharing 
