package ir.mahdiparastesh.mcdtp.time;

import ir.mahdiparastesh.mcdtp.FontCustomiser;

interface TimePickerController extends FontCustomiser {

    boolean is24HourMode();

    TimePickerDialog.Version getVersion();

    void tryVibrate();

    boolean isOutOfRange(Timepoint time, int index);

    boolean isAmDisabled();

    boolean isPmDisabled();

    Timepoint roundToNearest(Timepoint time, Timepoint.TYPE type);
}
