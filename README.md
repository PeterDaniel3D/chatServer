## Team C3 - Chat Server/Client
 Authors: Peter, René, Simon, Wehba
 
 Description:
 En simpel chat server med en hardcoded bruger liste. Projektet er opdelt i to packages; en client 
 og en server del. Serveren består af en ChatServer klasse, der lytter til indkommende klient kald.
 Der startes en ny tråd for hver forbundne klient, hvor der instantieres en ClientHandler til
 at håndtere netop denne klient. Clienthandler opfylder kravsspecikationerne til nævnte protokoller:<br>
 Client; CONNECT, SEND, CLOSE
 <br>Server; ONLINE, MESSAGE, CLOSE
<br><br><br>

## Quick Start Project for the Chat - Server

Simple Maven Project which can be used for the Chat-CA 

Using this project as your start code will make deploying your server (the jar-file) to Digital Ocean a "no brainer" if you follow the instructions given here

https://docs.google.com/document/d/1aE1MlsTAAYksCPpI4YZu-I_uLYqZssoobsmA-GHmiHk/edit?usp=sharing 
