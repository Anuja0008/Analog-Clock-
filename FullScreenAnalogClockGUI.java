import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import javax.swing.*;

public class FullScreenAnalogClockGUI extends JPanel {
    private int clockSize;
    private int clockRadius;
    private int centerX;
    private int centerY;
    
    public FullScreenAnalogClockGUI() {
        Timer timer = new Timer(1000, e -> repaint());
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        clockSize = Math.min(screenSize.width, screenSize.height) - 100;
        clockRadius = clockSize / 2;
        centerX = screenSize.width / 2;
        centerY = screenSize.height / 2;

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw clock face
        g2d.setColor(Color.WHITE);
        g2d.fillOval(centerX - clockRadius, centerY - clockRadius, clockSize, clockSize);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(centerX - clockRadius, centerY - clockRadius, clockSize, clockSize);

        // Draw clock numbers
        g2d.setFont(new Font("Arial", Font.BOLD, clockSize / 20));
        for (int i = 1; i <= 12; i++) {
            int angle = (i - 3) * 30;
            int x = (int) (centerX + Math.cos(Math.toRadians(angle)) * (clockRadius - clockSize / 10));
            int y = (int) (centerY + Math.sin(Math.toRadians(angle)) * (clockRadius - clockSize / 10));
            String number = String.valueOf(i);
            g2d.drawString(number, x - g2d.getFontMetrics().stringWidth(number) / 2, y + g2d.getFontMetrics().getHeight() / 4);
        }

        // Get current time
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);
        int seconds = calendar.get(Calendar.SECOND);

        // Draw hour hand
        drawHand(g2d, (hours % 12 + minutes / 60.0) * 30, clockRadius - clockSize / 5, clockSize / 50);

        // Draw minute hand
        drawHand(g2d, (minutes + seconds / 60.0) * 6, clockRadius - clockSize / 10, clockSize / 75);

        // Draw second hand
        drawHand(g2d, seconds * 6, clockRadius - clockSize / 15, clockSize / 100);

        // Draw text between 10 and 2 o'clock positions
        String nameText = "Anuja Mahagamage";
        g2d.setFont(new Font("Arial", Font.PLAIN, clockSize / 25));
        int nameTextWidth = g2d.getFontMetrics().stringWidth(nameText);
        int nameTextHeight = g2d.getFontMetrics().getHeight();
        int nameY = centerY - clockSize / 6; // Adjust this value to position your name vertically
        g2d.drawString(nameText, centerX - nameTextWidth / 2, nameY);

        // Draw current date below the clock
        LocalDate today = LocalDate.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateText = today.format(dateFormatter);
        g2d.setFont(new Font("Arial", Font.PLAIN, clockSize / 30));
        int dateTextWidth = g2d.getFontMetrics().stringWidth(dateText);
        g2d.drawString(dateText, centerX - dateTextWidth / 2, centerY + clockRadius + clockSize / 10);
    }

    private void drawHand(Graphics2D g2d, double angle, int length, int width) {
        angle = Math.toRadians(angle - 90);
        int x = (int) (centerX + Math.cos(angle) * length);
        int y = (int) (centerY + Math.sin(angle) * length);
        g2d.setStroke(new BasicStroke(width));
        g2d.drawLine(centerX, centerY, x, y);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Full Screen Analog Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.add(new FullScreenAnalogClockGUI());
        frame.setVisible(true);
    }
}
