import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * MLDemo - A simple Machine Learning demo in Java
 * Demonstrates basic linear regression and data processing
 */
public class MLDemo {
    
    // Simple Linear Regression Model
    static class LinearRegression {
        private double slope;
        private double intercept;
        
        public void train(List<Double> X, List<Double> y) {
            int n = X.size();
            double sumX = 0, sumY = 0, sumXY = 0, sumX2 = 0;
            
            // Calculate sums for linear regression formula
            for (int i = 0; i < n; i++) {
                sumX += X.get(i);
                sumY += y.get(i);
                sumXY += X.get(i) * y.get(i);
                sumX2 += X.get(i) * X.get(i);
            }
            
            // Calculate slope and intercept
            slope = (n * sumXY - sumX * sumY) / (n * sumX2 - sumX * sumX);
            intercept = (sumY - slope * sumX) / n;
            
            System.out.println("Model trained successfully!");
            System.out.println("Slope: " + slope);
            System.out.println("Intercept: " + intercept);
        }
        
        public double predict(double x) {
            return slope * x + intercept;
        }
    }
    
    // Data Generator
    static class DataGenerator {
        public static void generateSampleData(List<Double> X, List<Double> y, int size) {
            Random random = new Random(42);
            double trueSlope = 2.5;
            double trueIntercept = 10.0;
            
            for (int i = 0; i < size; i++) {
                double x = i + 1.0;
                double noise = random.nextGaussian() * 5; // Add some noise
                double yValue = trueSlope * x + trueIntercept + noise;
                
                X.add(x);
                y.add(yValue);
            }
        }
    }
    
    // Calculate Mean Squared Error
    public static double calculateMSE(List<Double> actual, List<Double> predicted) {
        double sum = 0;
        for (int i = 0; i < actual.size(); i++) {
            double diff = actual.get(i) - predicted.get(i);
            sum += diff * diff;
        }
        return sum / actual.size();
    }
    
    public static void main(String[] args) {
        System.out.println("=== Java Machine Learning Demo ===");
        System.out.println("Linear Regression Example\n");
        
        // Generate training data
        List<Double> X_train = new ArrayList<>();
        List<Double> y_train = new ArrayList<>();
        DataGenerator.generateSampleData(X_train, y_train, 50);
        
        System.out.println("Generated " + X_train.size() + " training samples");
        
        // Train the model
        LinearRegression model = new LinearRegression();
        model.train(X_train, y_train);
        
        // Make predictions on training data
        List<Double> predictions = new ArrayList<>();
        for (Double x : X_train) {
            predictions.add(model.predict(x));
        }
        
        // Calculate training error
        double mse = calculateMSE(y_train, predictions);
        System.out.println("\nTraining MSE: " + String.format("%.2f", mse));
        
        // Make sample predictions
        System.out.println("\n=== Sample Predictions ===");
        double[] testValues = {15.0, 25.0, 35.0};
        for (double testValue : testValues) {
            double prediction = model.predict(testValue);
            System.out.println(String.format("X=%.1f -> Predicted Y=%.2f", 
                testValue, prediction));
        }
        
        System.out.println("\n=== Demo Complete ===");
    }
}
