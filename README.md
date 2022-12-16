# Real Estate Android App

## Overview

The Real Estate Android App is a Android application designed to allow users to upload information about their estates to sell them or to allow other users to browse estates
currently for sale.
Using the app, users can view all estates currently in the system, view specific estates by clicking on their card in the app home, add an estate, update information about an estate already in the system or delete an estate from the system.
The application stores and reads the estate data from a JSON file.

## Installation
To install this application onto your local machine, simply clone the repository using the command and link featured below:
> git clone https://github.com/nicrowe00/Real_Estate_Android_App.git

If you are using a Windows machine you may need to use the git command line application to use the git commands which can be found here: https://git-scm.com/download/win

Download Android Studio from here: https://developer.android.com/studio

Set the minimum SDK in Android Studio to API 30 (Version 11.0)

Open the cloned git repository in Android Studio.

When the repository is opened in Android Studio, a file named "local.properties" will be created. To successfully use this application, a Google Maps API Key is required. The following is a guide to obtaining a key: https://developers.google.com/maps/documentation/android-api/start#get-key

Once the key has been obtained, enter it into the "local.properties" file in the following format:
> MAPS_API_KEY=Yourmapsapikey

The application can now be run from Android Studio.
