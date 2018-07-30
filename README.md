README Report
Milestone 3
Team #1544: GruPix+
Shreyas and Ruhani


Targeted Level of Achievement: Apollo 11 (Advanced)



Project Aim: 

An Android application which is able to group all the non-private pictures present in a user’s gallery by the faces of the people present in it, and display it in that order with labels accordingly. 



Scope:

The scope and implementation of the application has been reshaped a lot of time. We decided to keep the functionality wherein the user gets to view his gallery in the application. As before, the pictures are imported from the external storage on the device and are divided into album folders displayed in Card View. Each album has details of the pictures within it and the name (mostly the source) of the pictures, such as Camera, Downloads, WhatsApp Images, etc. The pictures displayed are updated each time the app is run. 
One opened, each album lists the photo in Grid View. The photos also have a basic preview functionality along with zooming feature. This ensures that the user can use basic gallery features within the application itself, thereby reducing the need to go back to the Android gallery when using this application.

Along with this, we have now successfully integrated Amazon Rekognition API to our Android application such that the users now have an option to view their gallery pictures sorted and grouped by the faces present in it. The API scans each picture for faces in it, and compares this with faces detected in all other pictures. It compares the pictures in pairs and recognizes those which seem of the same person as per a declared threshold (we have been using it at a rate of 90% similarity). The pictures are then brought together and displayed row by row in GridView. The pictures are given an arbitrary label of ‘Person 1’, ‘Person 2’, as of now. Each single picture has the facility to be opened and zoomed separately.

Once the application is launched, after displaying the logo, the homepage now consists of two named buttons which give the users the option to choose one of the two described features, leading them to the respective activities.



User Stories:

As a user, I want an application that can safely access all my gallery pictures and display them album-wise.
As a user, I want the application to give an option of grouping those pictures based on the faces present in it and display the result in a way I can see.
As a user, I want the application to give me an option in the beginning regarding which feature I want to view.



Core Features:

We have been able to deliver most of the intended core features for this final submission. They are as follows:

Logo and Homepage: The application logo and a transient homepage displaying the logo and name of the application. This displays the group icon for four seconds and automatically transits to the next working page.

Main Workpage: Here, the user are presented with two named buttons that give them a choice on what feature of the application to access. The rudimentary ‘Gallery’ feature allows them to view their phone gallery in the same album-wise sorted format. The other button displays the pictures of the gallery in a sorted manner, where faces which are similar are displayed and labelled consecutively. 

Gallery Option: The gallery page contains the albums stored on the external storage of the device. The pictures are divided into albums to give more control to the user and to replace the need to use the default gallery application on the android device. We have used card view to divide the gallery into albums, each displaying the number of pictures contained within and the name of the folder. When opened, each folder lists all the pictures inside it in a 2 column gridview, which is free to scroll and zoom. This is updated in real time, automatically when the app is launched. The photos also have preview functionality with zooming feature implemented. Thus, solving the issue of unnecessarily having to fall back to the default gallery application. For this example, we had downloaded 6 pictures of famous players on the emulator. The application then displays these under the album ‘Downloads’. Here, the pictures are displayed in the order in which they appear in the Android gallery, that is sorted by timestamp in a descending fashion.

Grouped Pictures Option: This options runs the user’s gallery images through the Amazon Rekognition API and returns the images sorted and displayed together, grouped by the faces present in it. The pictures here are displayed in a simple gridview, which allows the pictures to be expanded and zoomed. The  sample pictures we have taken consist of the three individual footballers: Neymar, Ronaldo, and Messi. The pictures were downloaded in arbitrary order, but are displayed sorted by face. As of now, the three individual people (as recognized by the API) are labelled Person 1, 2 and 3 respectively and shown in gridview.



Resources used for this Milestone: 

Android Studio: We used this working environment to create an application that can run on the Android O.S. We tested it using the virtual device Nexus 5 (API level 28).

Java: For the purpose of this project, we programme using the Java language. Android Studio along with Java forms an ideal starter pack to actualize a workable Android application.

Online Courses: From YouTube channels and Android Authority articles to courses in Udemy and Coursera, we have studied the software we are working with in great detail. This includes studying working with Studio, Java for Android, how Android internal storage works, amongst others.

Amazon Rekognition: An easy to use and integrate API provided by Amazon Web Services that provides a highly accurate facial analysis and recognition of the photos and videos provided to it. It provides a fast and real-time analysis of the pictures fed to it. Using the CompareFaces API function, it allows a comparison based on the confidence level indicated and gives similarity results.



Problems Encountered for this Milestone:

Difficulty in sticking to the intended schedule. We are slightly behind schedule because it was difficult to decide on which API to pursue. We tried few alternatives such as trying to integrate DLib in C++ with Heroku. But the difficulty made it tough to create an API thus we started exploring some pre-existing ones which could be utilized. We researched on OpenCV amongst the others and decided to get with Amazon Rekognition after a week of research.

Difficulty in integrating different parts of code written by the team members. And bug fixing arising from switching between versions and machines.

Inadequate planning of tentative schedule resulting in not being able to complete the some of the intended core feature Milestone 3 such as producing the pictures in albums and enabling a naming feature of the folder.
Tried out different versions for the same feature, for instance we tried using both recyclerview and grid view for displaying the pictures as a list but decided to use grid view in the end. This resulted in unnecessary time overheads as the code used for recyclerview wasn’t included in the present prototype. 

Also tried out different APIs, which later proved too complicated to integrate and hence cause time wastage.

Replanning the scope, such as whether or not to include a sync button and which implementation to carry out the grouping of pictures by, such as running the API on each picture every time.



Tasks performed by each member:

Shreyas:

Decided on the homepage and the logo.
Wrote the code for the transient homepage.
Wrote the code for importing the gallery pictures from the external storage of the device.
Wrote the code for displaying the pictures as albums using card view.
Designed the UI of the application.
Integrated different parts of code worked on individually by the team members.
Integrated AWS Mobile into the application so that the application will have access to the Cloud Computing services offered by AWS.
Integrated AWS Rekognition into the application for grouping the pictures.
Integrated AWS Cognito into the application to secure credentials of the app user.
Testing and debugging post integration and finishing up.

Ruhani:

Decided on the homepage and the logo.
Wrote the code for displaying the pictures using recyclerview which however wasn’t included in the present prototype.
Wrote the code for displaying the pictures using grid view.
Designed the preview functionality of the pictures.
Testing and debugging post integration and finishing up.



Tentative Schedule ahead:

By Splashdown and the final presentation of the project, we plan to include some more functionalities that we had planned for this sprint such as:

Displaying the grouped faces in albums.

Allowing the folder for each person to be named.

Search feature to search albums by the person name.

Deploying the application to Google Play Store.



Limitations of the current version:

The application currently cannot handle pictures without a human face. This limitation will however be worked on and fixed in time for Splashdown. 

The application will not be deployed to Google Play Store in time for the final Milestone submission as there exist a few glitches that will need to be fixed first.
