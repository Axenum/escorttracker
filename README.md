# Short-Instruction

The build is done with Gradle. Simple call

> gradlew clean build 

within the root of the project and your project is build.

Import he project in your favorite IDE by using its way to import a gradle project. Recommendation: Use [Android Studio](http://developer.android.com/tools/studio/index.html), since it has got the best integration for Android Projects. 

# Login-Handling

Via the Singleton UserHolder you can get the current logged in user. For instance, you can retrieve the user name of the current logged in user:

> UserHolder.getInstance().getCurrentUser().getUserName()

The current logged in user is still existing after an app restart. There is no expiry date of a logged in user.
