import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LinkShortener {

    private static HashMap<String, String> linkMap = new HashMap<>();

    // Method to generate a short link using base64 encoding of MD5 hash
    public static String shortenLink(String longUrl) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] mdBytes = md.digest(longUrl.getBytes());

            // Use base64 encoding for a URL-friendly short code
            String shortCode = Base64.getUrlEncoder().encodeToString(mdBytes);
            return shortCode.substring(0, 8); // Use the first 8 characters as the short link
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Method to save link mapping
    public static void saveLink(String shortLink, String longUrl) {
        linkMap.put(shortLink, longUrl);
    }

    // Method to get the long URL from a short link
    public static String getLongUrl(String shortLink) {
        return linkMap.get(shortLink);
    }

    // Main method with user interface
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Shorten a Link");
            System.out.println("2. Resolve a Short Link");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter the long URL: ");
                    String longUrl = scanner.nextLine();
                    String shortLink = shortenLink(longUrl);
                    saveLink(shortLink, longUrl);
                    System.out.println("Shortened Link: " + shortLink);
                    break;
                case 2:
                    System.out.print("Enter the short link: ");
                    String inputShortLink = scanner.nextLine();
                    String resolvedUrl = getLongUrl(inputShortLink);
                    if (resolvedUrl != null) {
                        System.out.println("Resolved URL: " + resolvedUrl);
                    } else {
                        System.out.println("Invalid short link.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
