# Multi-Calendar Date Time Picker

Multi-Calendar Date Time Picker is a forked version of 
[Material Date Time Picker](https://github.com/wdullaer/MaterialDateTimePicker),
with the ability to support any subclass of
[android.icu.util.Calendar](https://developer.android.com/reference/android/icu/util/Calendar)
as its default calendar. (Support for Android 7.0 and up)

I also added font resources support to it and migrated it to AndroidX, with up-to-date dependencies.

The library
uses [the code from the Android frameworks](https://android.googlesource.com/platform/frameworks/opt/datetimepicker/) as
a base and tweaked it to be as close as possible to Material Design example.

**Version 2 Layout**

 Date Picker                                                                                               | Time Picker                                                                                               
-----------------------------------------------------------------------------------------------------------|-----------------------------------------------------------------------------------------------------------
 ![Date Picker](https://raw.github.com/wdullaer/MaterialDateTimePicker/gh-pages/images/date_picker_v2.png) | ![Time Picker](https://raw.github.com/wdullaer/MaterialDateTimePicker/gh-pages/images/time_picker_v2.png) 

**Version 1 Layout**

 Date Picker                                                                                            | Time Picker                                                                                            
--------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------
 ![Date Picker](https://raw.github.com/wdullaer/MaterialDateTimePicker/gh-pages/images/date_picker.png) | ![Time Picker](https://raw.github.com/wdullaer/MaterialDateTimePicker/gh-pages/images/time_picker.png) 

## Using Material Date/Time Pickers

The library follows the same API as other pickers in the Android framework.
For a basic implementation, you'll need to

1. Implement an `OnTimeSetListener`/`OnDateSetListener`
2. Create a `TimePickerDialog`/`DatePickerDialog` using the supplied factory
3. Theme the pickers

### Implement an `OnTimeSetListener`/`OnDateSetListener`

In order to receive the date or time set in the picker, you will need to implement the `OnTimeSetListener` or
`OnDateSetListener` interfaces. Typically this will be the `Activity` or `Fragment` that creates the Pickers. The
callbacks use the same API as the standard Android pickers.

```java
@Override
public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
  String time = "You picked the following time: "+hourOfDay+"h"+minute+"m"+second;
  timeTextView.setText(time);
}

@Override
public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
  String date = "You picked the following date: "+dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
  dateTextView.setText(date);
}
```

### Create a `TimePickerDialog`/`DatePickerDialog` using the supplied factory

You will need to create a new instance of `TimePickerDialog` or `DatePickerDialog` using the static `newInstance()`
method, supplying proper default values and a callback. Once the dialogs are configured, you can call `show()`.

```java
Calendar now = Calendar.getInstance();
DatePickerDialog dpd = DatePickerDialog.newInstance(
  MainActivity.this,
  now.get(Calendar.YEAR), // Initial year selection
  now.get(Calendar.MONTH), // Initial month selection
  now.get(Calendar.DAY_OF_MONTH) // Inital day selection
);
// If you're calling this from a support Fragment
dpd.show(getFragmentManager(), "Datepickerdialog");
// If you're calling this from an AppCompatActivity
// dpd.show(getSupportFragmentManager(), "Datepickerdialog");
```

### Theme the pickers

The library contains 2 layout versions for each picker.

* Version 1: this is the original layout. It is based on the layout google used in the kitkat and early material design
  era
* Version 2: this layout is based on the guidelines google posted when launching android marshmallow. This is the
  default and still the most current design.

You can set the layout version using the factory

```java
dpd.setVersion(DatePickerDialog.Version.VERSION_2);

## Additional Options

### [TimePickerDialog] `setTitle(String title)`

Shows a title at the top of the `TimePickerDialog`

### [DatePickerDialog] `setTitle(String title)`

Set a custom text for the dialog Ok and Cancel labels. Can take a resourceId of a String. Works in both the
DatePickerDialog and TimePickerDialog

### [DatePickerDialog] `setMinDate(Calendar day)`

Set the minimum valid date to be selected. Date values before this date will be deactivated

### [DatePickerDialog] `setMaxDate(Calendar day)`

Set the maximum valid date to be selected. Date values after this date will be deactivated

### [TimePickerDialog] `setMinTime(Timepoint time)`

Set the minimum valid time to be selected. Time values earlier in the day will be deactivated

### [TimePickerDialog] `setMaxTime(Timepoint time)`

Set the maximum valid time to be selected. Time values later in the day will be deactivated

### [TimePickerDialog] `setSelectableTimes(Timepoint[] times)`

You can pass in an array of `Timepoints`. These values are the only valid selections in the
picker. `setMinTime(Timepoint time)`, `setMaxTime(Timepoint time)` and `setDisabledTimes(Timepoint[] times)` will
further trim this list down. Try to specify Timepoints only up to the resolution of your picker (i.e. do not add seconds
if the resolution of the picker is minutes).

### [TimePickerDialog] `setDisabledTimes(Timepoint[] times)`

You can pass in an array of `Timepoints`. These values will not be available for selection. These take precedence
over `setSelectableTimes` and `setTimeInterval`. Be careful when using this without selectableTimes: rounding to a valid
Timepoint is a very expensive operation if a lot of consecutive Timepoints are disabled. Try to specify Timepoints only
up to the resolution of your picker (i.e. do not add seconds if the resolution of the picker is minutes).

### [TimePickerDialog] `setTimeInterval(int hourInterval, int minuteInterval, int secondInterval)`

Set the interval for selectable times in the TimePickerDialog. This is a convenience wrapper
around `setSelectableTimes`. The interval for all three time components can be set independently. If you are not using
the seconds / minutes picker, set the respective item to 60 for better performance.

### [TimePickerDialog] `setTimepointLimiter(TimepointLimiter limiter)`

Pass in a custom implementation of `TimeLimiter`
Disables `setSelectableTimes`, `setDisabledTimes`, `setTimeInterval`, `setMinTime` and `setMaxTime`

### [DatePickerDialog] `setSelectableDays(Calendar[] days)`

You can pass a `Calendar[]` to the `DatePickerDialog`. The values in this list are the only acceptable dates for the
picker. It takes precedence over `setMinDate(Calendar day)` and `setMaxDate(Calendar day)`

### [DatePickerDialog] `setDisabledDays(Calendar[] days)`

The values in this `Calendar[]` are explicitly disabled (not selectable). This option can be used together
with `setSelectableDays(Calendar[] days)`: in case there is a clash `setDisabledDays(Calendar[] days)` will take
precedence over `setSelectableDays(Calendar[] days)`

### [DatePickerDialog] `setHighlightedDays(Calendar[] days)`

You can pass a `Calendar[]` of days to highlight. They will be rendered in bold. You can tweak the color of the
highlighted days by overwriting `date_picker_text_highlighted`

### [DatePickerDialog] `showYearPickerFirst(boolean yearPicker)`

Show the year picker first, rather than the month and day picker.

### [All] `OnDismissListener` and `OnCancelListener`

Both pickers can be passed a `DialogInterface.OnDismissLisener` or `DialogInterface.OnCancelListener` which allows you
to run code when either of these events occur.

```java
tpd.setOnCancelListener(new DialogInterface.OnCancelListener() {
    @Override
    public void onCancel(DialogInterface dialogInterface) {
      Log.d("TimePicker", "Dialog was cancelled");
    }
});
```

### [All] `vibrate(boolean vibrate)`

Set whether the dialogs should vibrate the device when a selection is made. This defaults to `true`.

### [All] `dismissOnPause(boolean dismissOnPause)`

Set whether the picker dismisses itself when the parent Activity is paused or whether it recreates itself when the
Activity is resumed.

### [All] `setLocale(Locale locale)`

Allows the client to set a custom locale that will be used when generating various strings in the pickers. By default
the current locale of the device will be used. Because the pickers will adapt to the Locale of the device by default you
should only have to use this in very rare circumstances.

### [DatePickerDialog] `autoDismiss(boolean autoDismiss)`

If set to `true` will dismiss the picker when the user selects a date. This defaults to `false`.

### [TimepickerDialog] `enableSeconds(boolean enableSconds)` and `enableMinutes(boolean enableMinutes)`

Allows you to enable or disable a seconds and minutes picker on the `TimepickerDialog`. Enabling the seconds picker,
implies enabling the minutes picker. Disabling the minute picker will disable the seconds picker. The last applied
setting will be used. By default `enableSeconds = false` and `enableMinutes = true`.

### [DatePickerDialog] `setTimeZone(Timezone timezone)` *deprecated*

Sets the `Timezone` used to represent time internally in the picker. Defaults to the current default Timezone of the
device.
This method has been deprecated: you should use the `newInstance()` method which takes a Calendar set to the appropriate
TimeZone.

### [DatePickerDialog] `setDateRangeLimiter(DateRangeLimiter limiter)`

Provide a custom implementation of DateRangeLimiter, giving you full control over which days are available for
selection. This disables all of the other options that limit date selection.

### `getOnTimeSetListener()` and `getOnDateSetListener()`

Getters that allow the retrieval of a reference to the callbacks currently associated with the pickers

### [DatePickerDialog] `setScrollOrientation(ScrollOrientation scrollOrientation)` and `getScrollOrientationi()`

Determines whether months scroll `Horizontal` or `Vertical`. Defaults to `Horizontal` for the v2 layout and `Vertical`
for the v1 layout

## FAQ

### Why does the `DatePickerDialog` return the selected month -1?

In the java `Calendar` class months use 0 based indexing: January is month 0, December is month 11. This convention is
widely used in the java world, for example the native Android DatePicker.

### How do I use a different version of a support library in my app?

This library depends on some androidx support libraries. Because the jvm allows only one version of a fully namespaced
class to be loaded, you will run into issues if your app depends on a different version of a library than the one used
in this app. Gradle is generally quite good at resolving version conflicts (by default it will retain the latest version
of a library), but should you run into problems (eg because you disabled conflict resolution), you can disable loading a
specific library for MaterialDateTimePicker.

Using the following snippet in your apps `build.gradle` file you can exclude this library's transitive appcompat library
dependency from being installed.

```groovy
implementation ('com.wdullaer:materialdatetimepicker:4.2.3') {
        exclude group: 'androidx.appcompat'
        exclude group: 'androidx.recyclerview'
}
```

MaterialDateTimepicker uses the following androidx libraries:

```groovy
implementation 'androidx.appcompat:appcompat:1.0.2'
implementation 'androidx.recyclerview:recyclerview:1.0.0'
```

Excluding a dependency will work fine as long as the version your app depends on is recent enough and google doesn't
release a version in the future that contains breaking changes. (If/When this happens I will try hard to document this).
See issue [#338](https://github.com/wdullaer/MaterialDateTimePicker/issues/338) for more information.

### How do I turn this into a year and month picker?

This DatePickerDialog focuses on selecting dates, which means that it's central design element is the day picker. As
this calendar like view is the center of the design it makes no sense to try and disable it. As such selecting just
years and months, without a day, is not in scope for this library and will not be added.

### How do I select multiple days?

The goal of this library is to implement the Material Design Date picker. This design is focused on picking exactly 1
date (with a large textual representation at the top). It would require quite a bit of redesigning to make it useful to
select multiple days. As such this feature is currently out of scope for this library and will not be added. If you
happen to make a library that implements this, based on this code or not, drop me a line and I'll happily link to it.

### How do I use my custom logic to enable/disable dates?

`DatePickerDialog` exposes some utility methods to enable / disable dates for common scenario's. If your needs are not
covered by these, you can supply a custom implementation of the `DateRangeLimiter` interface.
Because the `DateRangeLimiter` is preserved when the `Dialog` pauzes, your implementation must also
implement `Parcelable`.

```java
class MyDateRangeLimiter implements DateRangeLimiter {
  public MyDateRangeLimiter(Parcel in) {

  }

  @Override
  public int getMinYear() {
    return 1900;
  }

  @Override
  public int getMaxYear() {
    return 2100;
  }

  @Override
  public Calendar getStartDate() {
    Calendar output = Calendar.newInstance();
    output.set(Calendar.YEAR, 1900);
    output.set(Calendar.DAY_OF_MONTH, 1);
    output.set(Calendar.MONTH, Calendar.JANUARY);
    return output;
  }

  @Override
  public Calendar getEndDate() {
    Calendar output = Calendar.newInstance();
    output.set(Calendar.YEAR, 2100);
    output.set(Calendar.DAY_OF_MONTH, 1);
    output.set(Calendar.MONTH, Calendar.JANUARY);
    return output;
  }

  @Override
  public boolean isOutOfRange(int year, int month, int day) {
    return false;
  }

  @Override
  public Calendar setToNearestDate(Calendar day) {
      return day;
  }

  @Override
  public void writeToParcel(Parcel out) {

  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Parcelable.Creator<MyDateRangeLimiter> CREATOR
        = new Parcelable.Creator<MyDateRangeLimiter>() {
    public MyDateRangeLimiter createFromParcel(Parcel in) {
        return new MyDateRangeLimiter(in);
    }

    public MyDateRangeLimiter[] newArray(int size) {
        return new MyDateRangeLimiter[size];
    }
  };
}
```

When you provide a custom `DateRangeLimiter` the built-in methods for setting the enabled / disabled dates will no
longer work. It will need to be completely handled by your implementation.

## License

    Copyright (c) 2015 Wouter Dullaert

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
