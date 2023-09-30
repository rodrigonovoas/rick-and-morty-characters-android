# rick-and-morty-characters-android
Android app made with Kotlin and XML to show Rick and Morty characters retrieved from https://rickandmortyapi.com


Architecture

The architecture used is MVVM, currently the most used android architecture and the one powered by Google.
This architecture helps you to manage asynchronous data easily thanks to the use of livedata variables, the application of the observable pattern and the manage 
of background tasks with coroutines, among other things.

Patterns

- Observable pattern

- Singleton pattern

- Adapter pattern

Some strategies

Repository strategy to retrieve data from any kind of repository in a cleanear way thanks to its separation of concerns and the use of an interface which helps with the possibility to use multiple data sources. 


Notable libraries used in this project:
- Retrofit for network calls
- Koin for Dependency Injection
- LiveData, Flow and ViewModel  to manage asynchrnous data
- Glide to load images from url
- Navigation components
- Timber to help with loggins

