package main.util.snackbar;

import main.components.SnackBar;

public abstract class SnackBarBase implements SnackBarWrapper {
    private final static int MILLIS_DEFAULT = 6000;

    protected final SnackBar snackBar;
    protected final int millisToShow;

    protected SnackBarBase(SnackBar snackBar, int millisToShow) {
        this.snackBar = snackBar;
        this.millisToShow = millisToShow;
    }

    protected  SnackBarBase(SnackBar snackBar) {
        this.snackBar = snackBar;
        this.millisToShow = MILLIS_DEFAULT;
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
