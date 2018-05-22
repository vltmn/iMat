package main.util.snackbar;

import main.components.SnackBar;

public abstract class SnackBarBase implements SnackBarWrapper {
    protected final SnackBar snackBar;
    protected final int millisToShow;

    protected SnackBarBase(SnackBar snackBar, int millisToShow) {
        this.snackBar = snackBar;
        this.millisToShow = millisToShow;
    }

    @Override
    public SnackBar getSnackBar() {
        return snackBar;
    }

    @Override
    public int getMillisToShow() {
        return millisToShow;
    }
}
