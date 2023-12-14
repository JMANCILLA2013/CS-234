import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class JobIDGenerator {
  private Set<String> generatedIds = new HashSet<>();
  private Random random = new Random();

  public String generateJobID() {
      int length = random.nextInt(6) + 5; // Generates random length between 5 and 10 inclusive
      StringBuilder jobIdBuilder = new StringBuilder();

      do {
          jobIdBuilder.setLength(0); // Clear StringBuilder
          for (int i = 0; i < length; i++) {
              jobIdBuilder.append(random.nextInt(10)); // Append random digit (0-9) to the JobID
          }
      } while (generatedIds.contains(jobIdBuilder.toString())); // Regenerate if ID already exists

      String jobId = jobIdBuilder.toString();
      generatedIds.add(jobId);
      return jobId;
  }
}