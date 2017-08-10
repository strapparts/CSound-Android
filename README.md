# CSound-Android
A basic project to connect Csound to Android Studio.

This app would help the beginners to use Csound with Android.
It's an examples collection with explication comments.
A guide to installation of Csound library on Android Studio is on Csound Installer, inside csound-android[-version-number] folder.
The first pass is to read this guide appronted by Victor Lazzarini, Steven Yi and Martin O’Shea.
Here are some more details:

- .gitignore.
On Git, that is a methodology for working togheter in a project on the web, there's a file named .gitignore where you have to write all file that Git have to ignore when it upgrade the project.
With CsoundLibrary you don't use only one .gitignore file, but three.
The first appears when you open the project folder... [to finish]
The second is into the subfolder 'app'... [to finish]
The third is into the subfolder of library named 'CsoundAndroid'...
In this .gitignore file is wrote to ignore csnd library, because the library is wery large and the library shoud be loaded on Git... [to finish]

- permission reading file.
If you wont use opcodes that read external files from Csound, like .wav or .sf2 files, you can't not do this placing all thoose files on the 'res/raw' folder, due to prohibitions imposed by the security system. So you have to copy those file on SDcard or on the Cache. To avoid an accidental loss of those files, we suggest to copy these on the cache, according to the method copyFilesToInternalStorage() developed by Antonio Lanciotti, reproduced in this project.   

- bluetooth.
As he explains Brian Redfern: "That's a basic problem with bluetooth audio used for interactive music apps, for audio players and youtube its fine, they use huge buffers so you don't notice the latency. For interactive music you need to go with the 8th inch audio output or a class compliant usb device for good audio quality on android".

We invite anyone to improve the examples of this project or to add other activityes to introduce other possibilityes of application of Csound on Android.

thank you to Guillermo Senna, for help to use of updateValuesFromCsound() and naturally Steven Ji, that he dedicated himself specifically to the connection system beetwen Csound and Android.

2017 August, Antonio Lanciotti and Andrea Strappa
