# CSound-Android
A basic project to connect Csound to Android Studio.

This app would help the beginners to use Csound with Android.
It's a collection of examples with explicative comments.
A guide to include the Csound-android library on Android Studio is in the library itself, inside csound-android[-version-number] folder.
The first pass is to read this guide appronted by Victor Lazzarini, Steven Yi and Martin O’Shea.
Here there are some more details:

- .gitignore.
In a Git repository, there is a (or more than one) file named .gitignore where you have to write all the files that Git have to ignore when the project is updated.
Each .gitignore file works only in the directory where it is placed and in the subfolders.
The first .gitignore file is always in the main folder of the project and so works everywhere: we used the site https://www.gitignore.io/ to create a .gitignore file for the project.
The second is into the subfolder 'app' and is needed to ignore the subfolder 'build' where every compilation file and package will be stored.
Inside the CsoundLibrary there is another .gitignore file, wrote to ignore 'csnd6' folder, because the library is very large and should not be loaded on Git. This means that if you want fork or clone this repo you have to replace the folder 'CsoundAndroid' with the original one you can find in the library.

- permission reading file.
If you want to use opcodes to read external files from Csound, like .wav or .sf2 files, you can't do this placing all those files in the 'res/raw' folder, due to prohibitions imposed by the Android security system. So you have to copy those file in the internal storage of the app or in the external storage. To avoid an accidental loss of those files, we suggest to copy these on the internal storage, according to the method copyFilesToInternalStorage() developed by Antonio Lanciotti, used in this project.   

- bluetooth.
As he explains Brian Redfern: "That's a basic problem with bluetooth audio used for interactive music apps, for audio players and youtube its fine, they use huge buffers so you don't notice the latency. For interactive music you need to go with the 8th inch audio output or a class compliant usb device for good audio quality on android".

- screen orientation.
We recommend to set the orientation in portrait mode, because if the device is rotated when Csound is running it can't be stopped anymore. We blocked the orientation using the instruction " setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); " that has to be written in the method 'onCreate()' in each activity. Because every activity extends BaseCsoundActivity we just had to write it in onCreate() method in the 'BaseCsoundActivity.java' file. 

We invite anyone to improve the examples of this project or to add other activities to introduce other possibilities of application of Csound on Android.

Thank you to Guillermo Senna, for the help in the use of updateValuesFromCsound() and naturally to Steven Yi, that dedicated himself specifically to the connection system between Csound and Android.

2017 August, Antonio Lanciotti and Andrea Strappa