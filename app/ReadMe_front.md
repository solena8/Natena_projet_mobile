# Android App Structure and Interaction

## File Structure

### Kotlin Files
- **Spot.kt**: Defines the `Spot` data class, representing the core model of a surf spot.
- **SpotDto.kt**: Contains the `SpotDto` data class for parsing API responses.
- **SurfSpotApiService.kt**: Defines the API service interface and network functions using Retrofit.
- **SpotAdapter.kt**: Custom adapter for displaying spots in a ListView.
- **MainActivity.kt**: Main activity handling the list of spots.
- **SingleSpotActivity.kt**: Activity for displaying details of a single spot.

### XML Layout Files
- **activity_main.xml**: Layout for the main activity, containing a ListView.
- **activity_single_spot.xml**: Layout for the single spot detail view.
- **spot_layout.xml**: Layout for individual spot items in the ListView.

### Configuration Files
- **AndroidManifest.xml**: Declares app components, permissions, and essential information.
- **build.gradle**: Defines project structure, dependencies, and build configurations.
- **local.properties**: Contains local configuration like SDK path (not version controlled).

## Interaction Flow

1. `MainActivity` uses `SurfSpotApiService` to fetch spot data from the API.
2. API returns data as `SpotDto`, which is converted to `Spot` objects.
3. `SpotAdapter` displays `Spot` objects in the ListView defined in `activity_main.xml`.
4. User clicks on a spot, launching `SingleSpotActivity` with `activity_single_spot.xml`.
5. `spot_layout.xml` defines the appearance of each spot in the list view.

This structure follows MVVM or MVC patterns, separating data, UI, and business logic.

Adding a form :
Create a new Activity for the form:
Right-click on your package in Android Studio
New -> Activity -> Empty Activity
Name it AddSpotActivity

Create a new layout file for the form:
Right-click on the res/layout folder
New -> Layout Resource File
Name it activity_add_spot

Modify activity_add_spot.xml:
Add EditText fields for surf break type, address, image URL, difficulty level, latitude, and longitude
Add a Button for submitting the form

Update AddSpotActivity.kt:
Initialize the UI components
Handle form submission
Validate input
Create a new Spot object
Send the new Spot to the API

Modify MainActivity.kt:
Add a FloatingActionButton or a regular Button to navigate to AddSpotActivity

Update the onResume() method to refresh the spot list when returning from AddSpotActivity

Update SurfSpotApiService.kt:
Add a new POST method to send the new spot to the API

Update SpotDto.kt:
Add a difficulty field to the SpotDto class

Update Spot.kt:
Add a difficulty field to the Spot class

Update AndroidManifest.xml:
Add the new AddSpotActivity to the manifest