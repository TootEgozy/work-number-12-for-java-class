public class Baby {
    // parameters.
    private String _firstName;
    private String _lastName;
    private String _id;
    private Date _dateOfBirth;
    private Weight _birthWeight;
    private Weight _currentWeight;
    
    // deafualt values.
    private static final int ID_LENGTH = 9;
    private static final String DEFAULT_ID = "000000000";
    
    // weight constants of the minimal healthy weight for each growth priod of the baby.
    private static final int TWO_MONTHS_WEIGHT_ADDITION = 30;
    private static final int FOUR_MONTHS_WEIGHT_ADDITION = 25;
    private static final int EIGHT_MONTHS_WEIGHT_ADDITION = 16;
    private static final int YEAR_WEIGHT_ADDITION = 8;

    // main constructor for baby.
    public Baby(String fName, String lName, String id,
    int day, int month, int year, int birthWeightInGrams) {
        this._firstName = fName;
        this._lastName = lName;
        this._id = this.sanitizeId(id);
        this._dateOfBirth = new Date(day, month, year);
        this._birthWeight = new Weight(birthWeightInGrams);
        this._currentWeight = new Weight(this._birthWeight);
    }
    
    // copy constructor.
    public Baby(Baby other) {
        this._firstName = other._firstName;
        this._lastName = other._lastName;
        this._id = other._id;
        this._dateOfBirth = new Date(other._dateOfBirth);
        this._birthWeight = new Weight(other._birthWeight);
        this._currentWeight = new Weight(this._birthWeight);
    }
    
    // gets the first name.
    public String getFirstName() {
        return this._firstName;
    }
    
    // gets the last name.
    public String getLastName() {
        return this._lastName;
    }
    
    // gets the id.
    public String getId() {
        return this._id;
    }
    
    // gets the date of birth.
    public Date getDateOfBirth() {
        return this._dateOfBirth;
    }
    
    // gets the birth weight.
    public Weight getBirthWeight() {
        return this._birthWeight;
    }
    
    // gets the current weight.
    public Weight getCurrentWeight() {
        return this._currentWeight;
    }

    // util method to sanitize the id input.
    // if it's of valid length, returns the id. otherwise retuns the default id.
    private String sanitizeId(String id) {
        if(id.length() == ID_LENGTH) return id;
        else return DEFAULT_ID;
    }
    
    // util method to convert the baby weight into grams to assist calculations.
    private int weightToGrams(Weight weight) {
        return weight.getGrams() + (weight.getKilos() * 1000);
    }
    
    // sets the current weight if the given parameter is valid.
    public void setCurrentWeight(Weight weightToSet) {
        if(weightToSet.getKilos() >= 1) {
            this._currentWeight = new Weight(weightToSet);
        }
    }
    
    // returns a String that represents this baby.
    public String toString() {
         return "Name: "+ this.getFirstName() + " " + this.getLastName() + "\n"
        + "Id: " + this.getId() + "\n"
        + "Date of Birth: " + this.getDateOfBirth().toString() + "\n"
        + "Birth Weight: " + this.getBirthWeight().toString() + "\n" 
        + "Current Weight: "+ this.getCurrentWeight().toString() + "\n";
    }
    
    // checks if two babies are the same. Two babies are considered the same if they have 
    // identical names, ids and dates of birth.
    public boolean equals(Baby other) {
        return (this.getFirstName().equals(other.getFirstName())) &&
        (this.getLastName().equals(other.getLastName())) &&
        (this.getDateOfBirth().equals(other.getDateOfBirth())) &&
        (this.getId().equals(other.getId()));
    }
    
    // checks if two babies are twins. twins have the same last name, different first names, different IDs 
    // and similar dates of birth or difference of one day between their dates of birth.
    public boolean areTwins(Baby other) {
        boolean lNames = this.getLastName().equals(other.getLastName());
        boolean fNames = !this.getFirstName().equals(other.getFirstName());
        boolean ids = !this.getId().equals(other.getId());
        boolean datesOfBirth = (this.getDateOfBirth().equals(other.getDateOfBirth())) ||
        (this.getDateOfBirth().equals(other.getDateOfBirth().tomorrow())) ||
        (this.getDateOfBirth().tomorrow().equals(other.getDateOfBirth()));
        
        return lNames && fNames && ids && datesOfBirth;
    }
    
    // checks if the weight of this baby is heavier than the weight of another baby.
    public boolean heavier(Baby other) {
        return this.getCurrentWeight().heavier(other.getCurrentWeight());
    }
    
    // updates the baby's current weight by adding the additional grams.
    // If the sum of the current weight is invalid, doesn't update it.
    public void updateCurrentWeight (int grams){
        this.setCurrentWeight(this.getCurrentWeight().add(grams));
    }
    
    // checks if the date of birth of this baby is before than the date of birth of another baby.
    public boolean older (Baby other) {
        return this.getDateOfBirth().before(other.getDateOfBirth());
    }
    
    // util method to get the minimum healthy weight for a baby in the range 1-7 days old.
    private double getFirstWeekMin(int ageInDays) {
        int birthWeight = this.weightToGrams(this.getBirthWeight());
        double dailyReduction = (birthWeight / 10.0 / 7);
        return birthWeight - (dailyReduction * ageInDays);
    }
    
    // util method to get the minimum healthy weight for a baby in the range 8-60 days old.
    private double getTwoMonthsMin(int ageInDays) {
        int daysInRange = ageInDays - 7; // don't include the first week.
        
        return this.getFirstWeekMin(7) + (daysInRange * TWO_MONTHS_WEIGHT_ADDITION);
    }
    
    // util method to get the minimum healthy weight for a baby in the range 60-240 days old.
     private double getFourMonthsMin(int ageInDays) {
        int daysInRange = ageInDays - 60; // don't include the first 2 months.
        
        return this.getTwoMonthsMin(60) + (daysInRange * FOUR_MONTHS_WEIGHT_ADDITION);
    }
    
    // util method to get the minimum healthy weight for a baby in the range 120-240 days old.
     private double getEightMonthsMin(int ageInDays) {
        int daysInRange = ageInDays - 120; // don't include the first 4 months.
        
        return this.getFourMonthsMin(120) + (daysInRange * EIGHT_MONTHS_WEIGHT_ADDITION);
    }
    
    // util method to get the minimum healthy weight for a baby in the range 240-365 days old.
     private double getYearMin(int ageInDays) {
        int daysInRange = ageInDays - 240; // don't include the first 8 months.
        
        return this.getEightMonthsMin(240) + (daysInRange * YEAR_WEIGHT_ADDITION);
    }
    
    // checks if the current weight of this baby is within the valid range.
    public int isWeightInValidRange (int numOfDays) {
        final int CURRENT_WEIGHT = this.weightToGrams(this.getCurrentWeight());

        if((numOfDays < 1) || (numOfDays > 365)) return 1; // if number of days is invalid return 1.
        
        // go over the possible ranges of age and in each, check if currentWeight is above the minimal healthy weight for it.
        if(numOfDays < 8) {
            return (CURRENT_WEIGHT < this.getFirstWeekMin(numOfDays)) ? 2 : 3;
        } else if(numOfDays < 61) {
            return (CURRENT_WEIGHT < this.getTwoMonthsMin(numOfDays)) ? 2 : 3;
        } else if(numOfDays < 121) {
            return (CURRENT_WEIGHT < this.getFourMonthsMin(numOfDays)) ? 2 : 3;
        } else if(numOfDays <= 241) {
            return (CURRENT_WEIGHT < this.getEightMonthsMin(numOfDays)) ? 2: 3;
        } else {
            return (CURRENT_WEIGHT < this.getYearMin(numOfDays)) ? 2 : 3;
        }        
    }
}