package ir.mahdiparastesh.mcdtp;

import androidx.annotation.FontRes;

public interface FontCustomiser {

    @FontRes
    Integer getBoldFont();

    @FontRes
    Integer getNormalFont();
}
