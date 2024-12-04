public class Date {
    private int _day; // the day in the month (1-31).
    private int _month; // the month in the year (1-12).
    private int _year; // the year (4 digits).
    
    // only years with 4 digits are allowed
    private static final int MIN_YEAR = 1000;
    private static final int MAX_YEAR = 9999;
    
    // default date.
    private static final int DEFAULT_DAY = 1;
    private static final int DEFAULT_MONTH = 1;
    private static final int DEFAULT_YEAR = 2024;
    
    
    // date constructor. 
    // if the date is valid, creates a new Date with the given parameters
    // if not, creates the default Date (01/01/2024).
    public Date(int day, int month, int year) {
        if (this.isDateValid(day, month, year)) {
            this._day = day;
            this._month = month;
            this._year = year;
        } else {     
            this._day = DEFAULT_DAY;
            this._month = DEFAULT_MONTH;
            this._year = DEFAULT_YEAR;
        }
 
    }
    
    // default constructor (01/01/2024).
    public Date() {
        this._day = DEFAULT_DAY;
        this._month = DEFAULT_MONTH;
        this._year = DEFAULT_YEAR;
    }
    
    // copy constructor to create a new Date from an existing Date instance (other).
    public Date(Date other) {
        this._day = other.getDay();
        this._month = other.getMonth();
        this._year = other.getYear();
    }
    
    // gets the day of this date.
    public int getDay() {
        return this._day;
    }
    
    // gets the month of this date.
    public int getMonth() {
        return this._month;
    }
    
    // gets the year of this date.
    public int getYear() {
        return this._year;
    }
    
    // receives a new day, if the outcome date is valid, sets the day.
    public void setDay(int dayToSet) {
        if (this.isDateValid(dayToSet, this._month, this._year)) {
            this._day = dayToSet;
        }
    }
    
    // receives a new month, if the outcome date is valid, sets the month.
    public void setMonth(int monthToSet) {
        if (this.isDateValid(this._day, monthToSet, this._year)) {
            this._month = monthToSet;
        }
    }
    
    // receives a new year, if the outcome date is valid, sets the year.
    public void setYear(int yearToSet) {
        if (this.isDateValid(this._day, this._month, yearToSet)) {
            this._year = yearToSet;
        }
    }
    
    // checks if the year is a leap year.
    private boolean isLeapYear (int y) {
        return (y%4==0 && y%100!=0) || (y%400==0) ? true : false;
    }
    
    // get the number of days in a month (month parameter must be 1-12).
    private int getDaysInMonth(int month, int year) {
        // each case returns the number of days for the month.
        // for example case 1 is for the first month (January) which have 31 days.
        switch (month) { 
            case 1: 
                return 31;
            case 2:
                return this.isLeapYear(year) ? 29 : 28;
            case 3: 
                return 31;
            case 4: 
                return 30;
            case 5: 
                return 31;
            case 6: 
                return 30;
            case 7: 
                return 31;
            case 8: 
                return 31;
            case 9: 
                return 30;
            case 10: 
                return 31;
            case 11: 
                return 30;
            case 12: 
                return 31;
            default: 
                return -1;
        }
    }
    
    // checks if date is valid and returns a boolean result.
    private boolean isDateValid (int day, int month, int year){
        
        // if month is not within the 1-12 range, then the date is invalid.
        if (month < 1 || month > 12) {
            return false;
        }
        
        // if the year is not 4 digits, then the date is invalid.
        if (year < MIN_YEAR || year > MAX_YEAR) {
            return false;
        }
        
        // get the number of days in this month.
        int numberOfDaysThisMonth = this.getDaysInMonth(month, year);
        
        // if the given day number is less then 1, or more then the days
        // which are defined for this month, then the date is invalid.
        if (day < 1 || day > numberOfDaysThisMonth) {
            return false;
        }
        
        // if we have passed the previous tests then the date is valid.
        return true;      
    }
    
    
    // checks if this date and another date (other) are the same, returns a boolean value.
    public boolean equals(Date other) {
        return (this._day == other._day) && (this._month == other._month) && (this._year == other._year);
    }
    
    
    // computes the day number since the beginning of the Christian counting of years.
    private int calculateDate ( int day, int month, int year) {
        if (month < 3) {
            year--;
            month = month + 12;
        }
        return 365 * year + year/4 - year/100 + year/400 + ((month+1) * 306)/10 + (day - 62);
    } 
    
    // checks if this date comes before another date (other).
    public boolean before(Date other) {
        int thisDayCount = this.calculateDate(this._day, this._month, this._year);
        int otherDayCount = this.calculateDate(other._day, other._month, other._year);
        return thisDayCount < otherDayCount;
    }
    
    // checks if this date comes after another date.
    public boolean after(Date other) {
        return other.before(this);
    }
    
    // calculates the difference in days between two dates.
    public int difference(Date other) {
        int thisDayCount = this.calculateDate(this._day, this._month, this._year);
        int otherDayCount = this.calculateDate(other._day, other._month, other._year);
        return Math.abs(thisDayCount - otherDayCount);
    }
    
    // returns a String that represents this date.
    public String toString() {
        return (this._day < 10 ? "0" : "") + String.valueOf(this._day) + "/" 
        + (this._month < 10 ? "0" : "") + String.valueOf(this._month) + "/"
        + String.valueOf(this._year);
    }
    
    // calculate the date of tomorrow
    public Date tomorrow() {
        int daysInMonth = this.getDaysInMonth(this._month, this._year);
        
        // if after adding a day we are still witin the days range for this month,
        // create a new Date with the same month and year, and add 1 to day.
        if (this._day + 1 <= daysInMonth) {
            return new Date(this._day + 1, this._month, this._year);
        }
        
        // if we need to advance a month, check if we are still in the same year.
        // if so, create a Date with the same year, the next month and the day is the first of the month.
        if (this._month + 1 <= 12) {
            return new Date(1, this._month + 1, this._year);        
        }
        
        // if we need to advance a year, create a Date which is the first of the following year.
        return new Date(1, 1, this._year + 1);

    }
}