public class Weight {
    
    private int _kilos;
    private int _grams;
    
    // minimal weight for a baby is 1 kg.
    // the deafult weight is also 1 kg.
    private static final int MIN_KILOS = 1;
    private static final int MIN_GRAMS = 0;
    private static final int MAX_GRAMS = 999;
    private static final int DEFAULT_KILOS = 1;
    private static final int DEFAULT_GRAMS = 0;
    
    // weight constructor - If the given weight is valid - creates a new Weight object, 
    // otherwise initialize it to 1 kilo.
    public Weight(int kilos, int grams) {
        if(this.validateWeight(kilos, grams)) {
            this._kilos = kilos;
            this._grams = grams;
        } else {
            this._kilos = DEFAULT_KILOS;
            this._grams = DEFAULT_GRAMS;
        }
    }
    
    // copy constructor.
    public Weight(Weight other) {
        this._kilos = other.getKilos();
        this._grams = other.getGrams();
    }
    
    // constructor with only the grams parameter.
    public Weight(int totalGrams) {
        int kilos = totalGrams / 1000;
        int grams = totalGrams % 1000;
        
        if(kilos >= MIN_KILOS) {
            this._kilos = kilos;
            this._grams = grams;  
        } else {
            this._kilos = DEFAULT_KILOS;
            this._grams = DEFAULT_GRAMS;
        }
    }    
    
    // gets the kilos.
    public int getKilos() {
        return this._kilos;
    }

    // gets the grams.
    public int getGrams() {
        return this._grams;
    }
    
    // util method to validate the weight.
    private boolean validateWeight(int kilos, int grams) {
        if (kilos < MIN_KILOS)  {
            return false;
        }
        
        if((grams < MIN_GRAMS) || (grams > MAX_GRAMS)) {
            return false;
        }
        
        return true;
    }
    
    // util method to get the weight in grams.
    private int toGrams() {
        return this.getGrams() + (this.getKilos() * 1000);
    }
    
    // checks if two weights are the same.
    public boolean equals(Weight other) {
        return (this.toGrams() == other.toGrams());
    }
    
    // checks if this weight is lighter then another weight.
    public boolean lighter(Weight other) {
        return this.toGrams() < other.toGrams();
    }
    
    // checks if this weight is heavier then another weight.
    public boolean heavier(Weight other) {
        return other.lighter(this);
    }
    
    // returns a string that represents this weight.
    public String toString() {
        double totalWeight = this.getKilos() + (this.getGrams() / 1000.0);
        
        return String.valueOf(totalWeight);
    }
    
    // adding the grams given as a parameter to this weight. 
    // if the result are valid returns the new weight.
    // if invalid returns this weight.
    public Weight add (int grams) {
        int totalGrams = this.toGrams() + grams;
        
        int kilos = totalGrams / 1000;
        int newGrams = totalGrams % 1000;
        
        if(this.validateWeight(kilos, newGrams)) {
            return new Weight(kilos, newGrams);
        } else {
            return new Weight(this);
        }
    }
}