# Countries
This application consists of three pages. 
- One of them home page that shows countries taken from API. 
- Second one is details page that shows the country details. 
- And the last one is saved countries page that shows the saved countries names.

You can easily query the countries on the home page. After that you can reach the details about the country with one click and if you want to save you can do it with one click.


## Overview
### Architecture
This project follows MVVM architecture

### API Calls
Using the following APIs:
- [RapidAPI GeoDB Cities](https://rapidapi.com/wirefreethought/api/geodb-cities)


## Technology
### Presentation
- [View Binding](https://developer.android.com/topic/libraries/view-binding) - generates binding classes which hold reference to XML id'd elements
- [Recycler View](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView) - Displays lists of elements
- [Hilt](https://dagger.dev/hilt/) - Dependency Injection library based on dagger
- [Jetpack Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) - Navigation library
- [SafeArgs](https://developer.android.com/guide/navigation/navigation-getting-started#ensure_type-safety_by_using_safe_args) - Type safe navigation
- [Glide](https://github.com/bumptech/glide) - An image loading and caching library

### Networking
- [Retrofit](https://square.github.io/retrofit/) - Rest Client
- [OkHttp](https://square.github.io/okhttp/) - Http Client

## Screens
Home Page             |  Saved Page |  Details Page
:-------------------------:|:-------------------------:|:-------------------------:
![](https://github.com/busrakizilaslan/Countries/blob/master/screenshots/home.png)|![](https://github.com/busrakizilaslan/Countries/blob/master/screenshots/saved.png)|![](https://github.com/busrakizilaslan/Countries/blob/master/screenshots/details.png)

