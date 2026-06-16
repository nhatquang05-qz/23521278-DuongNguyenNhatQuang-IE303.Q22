package Lab04;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

class Product {
    String name;
    String subtitle;
    String brand;
    String price;
    String imagePath;

    public Product(String name, String subtitle, String brand, String price, String imagePath) {
        this.name = name;
        this.subtitle = subtitle;
        this.brand = brand;
        this.price = price;
        this.imagePath = imagePath;
    }
}

class FadeImagePanel extends JPanel {
    private Image currentImage;
    private Image nextImage;
    private float alpha = 1.0f;
    private Timer timer;

    public FadeImagePanel() {
        setOpaque(false);
    }

    public void setImage(Image img) {
        this.currentImage = img;
        repaint();
    }

    public void transitionTo(Image newImg) {
        this.nextImage = newImg;
        this.alpha = 1.0f;
        
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        timer = new Timer(15, e -> {
            alpha -= 0.1f;
            if (alpha <= 0.0f) {
                alpha = 0.0f;
                timer.stop();
                currentImage = nextImage;
                nextImage = null;
                fadeIn();
            }
            repaint();
        });
        timer.start();
    }

    private void fadeIn() {
        timer = new Timer(15, e -> {
            alpha += 0.1f;
            if (alpha >= 1.0f) {
                alpha = 1.0f;
                timer.stop();
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        if (currentImage != null) {
            int imgW = currentImage.getWidth(null);
            int imgH = currentImage.getHeight(null);
            double ratio = Math.min((double) getWidth() / imgW, (double) getHeight() / imgH);
            int w = (int) (imgW * ratio);
            int h = (int) (imgH * ratio);
            int x = (getWidth() - w) / 2;
            int y = (getHeight() - h) / 2;
            g2d.drawImage(currentImage, x, y, w, h, null);
        }
        
        g2d.dispose();
    }
}

public class Lab4 extends JFrame {
    List<Product> products;
    Product currentProduct;
    
    JPanel leftPanel;
    FadeImagePanel imagePanel;
    JLabel nameLabel;
    JLabel priceLabel;
    JLabel brandLabel;
    JTextArea descArea;

    JPanel rightPanel;
    List<JPanel> cardPanels = new ArrayList<>();

    public Lab4() {
        products = new ArrayList<>();
        
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/products"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            if (json != null && json.length() > 2) {
                json = json.substring(1, json.length() - 1);
                String[] items = json.split("\\},\\{");
                for (String item : items) {
                    item = item.replace("{", "").replace("}", "");
                    String name = extractJsonValue(item, "name");
                    String subtitle = extractJsonValue(item, "subtitle");
                    String brand = extractJsonValue(item, "brand");
                    String price = extractJsonValue(item, "price");
                    String imagePath = extractJsonValue(item, "imagePath");
                    
                    if (imagePath.isEmpty()) {
                        imagePath = extractJsonValue(item, "image_path");
                    }
                    
                    products.add(new Product(name, subtitle, brand, price, imagePath));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (products.isEmpty()) {
            products.add(new Product("No Data", "No API Connection", "None", "$0.00", "images/img1.png"));
        }

        setTitle("Product Store");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        currentProduct = products.get(0);

        setupLeftPanel();
        setupRightPanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, new JScrollPane(rightPanel));
        splitPane.setDividerLocation(350);
        splitPane.setDividerSize(0);
        splitPane.setBorder(null);
        add(splitPane, BorderLayout.CENTER);
    }

    private String extractJsonValue(String jsonItem, String key) {
        String searchKey = "\"" + key + "\":\"";
        int start = jsonItem.indexOf(searchKey);
        if (start == -1) return "";
        start += searchKey.length();
        int end = jsonItem.indexOf("\"", start);
        if (end == -1) return "";
        return jsonItem.substring(start, end);
    }

    private void setupLeftPanel() {
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        imagePanel = new FadeImagePanel();
        
        java.net.URL imgUrl = getClass().getResource(currentProduct.imagePath);
        ImageIcon icon;
        if (imgUrl != null) {
            icon = new ImageIcon(imgUrl);
        } else {
            icon = new ImageIcon("Lab04/" + currentProduct.imagePath);
            if (icon.getIconWidth() == -1) {
                icon = new ImageIcon(currentProduct.imagePath);
            }
        }
        
        imagePanel.setImage(icon.getImage());
        imagePanel.setPreferredSize(new Dimension(300, 300));
        imagePanel.setMaximumSize(new Dimension(300, 300));

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(300, 1));

        nameLabel = new JLabel(currentProduct.name);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 22));
        
        priceLabel = new JLabel(currentProduct.price);
        priceLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        brandLabel = new JLabel(currentProduct.brand);
        brandLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        brandLabel.setForeground(Color.GRAY);

        descArea = new JTextArea("This product is excluded from all promotional discounts and offers.");
        descArea.setWrapStyleWord(true);
        descArea.setLineWrap(true);
        descArea.setEditable(false);
        descArea.setFont(new Font("Arial", Font.PLAIN, 12));
        descArea.setForeground(Color.GRAY);
        descArea.setBackground(Color.WHITE);
        descArea.setMaximumSize(new Dimension(300, 80));

        leftPanel.add(imagePanel);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(separator);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(nameLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(priceLabel);
        leftPanel.add(Box.createVerticalStrut(5));
        leftPanel.add(brandLabel);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(descArea);
    }

    private void setupRightPanel() {
        rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(0, 4, 15, 15));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            JPanel card = createCard(p, i == 0);
            cardPanels.add(card);
            rightPanel.add(card);
        }
    }

    private JPanel createCard(Product p, boolean isSelected) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout());
        card.setBackground(new Color(248, 248, 248));
        
        Border normalBorder = BorderFactory.createLineBorder(new Color(235, 235, 235), 1, true);
        Border selectedBorder = BorderFactory.createLineBorder(new Color(100, 150, 255), 2, true);
        card.setBorder(isSelected ? selectedBorder : normalBorder);

        JPanel topInfo = new JPanel();
        topInfo.setLayout(new BoxLayout(topInfo, BoxLayout.Y_AXIS));
        topInfo.setOpaque(false);
        topInfo.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        
        JLabel cName = new JLabel(p.name);
        cName.setFont(new Font("Arial", Font.BOLD, 12));
        JLabel cSub = new JLabel(p.subtitle);
        cSub.setFont(new Font("Arial", Font.PLAIN, 10));
        cSub.setForeground(Color.GRAY);
        
        topInfo.add(cName);
        topInfo.add(Box.createVerticalStrut(5));
        topInfo.add(cSub);

        java.net.URL imgUrl = getClass().getResource(p.imagePath);
        ImageIcon origIcon;
        if (imgUrl != null) {
            origIcon = new ImageIcon(imgUrl);
        } else {
            origIcon = new ImageIcon("Lab04/" + p.imagePath);
            if (origIcon.getIconWidth() == -1) {
                origIcon = new ImageIcon(p.imagePath);
            }
        }
        
        Image scaledImg = origIcon.getImage().getScaledInstance(120, 90, Image.SCALE_SMOOTH);
        JLabel imgLabel = new JLabel(new ImageIcon(scaledImg));
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel bottomInfo = new JPanel(new BorderLayout());
        bottomInfo.setOpaque(false);
        bottomInfo.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        
        JLabel cBrand = new JLabel(p.brand);
        cBrand.setFont(new Font("Arial", Font.PLAIN, 10));
        cBrand.setForeground(Color.GRAY);
        
        JLabel cPrice = new JLabel(p.price);
        cPrice.setFont(new Font("Arial", Font.BOLD, 14));

        bottomInfo.add(cBrand, BorderLayout.WEST);
        bottomInfo.add(cPrice, BorderLayout.EAST);

        card.add(topInfo, BorderLayout.NORTH);
        card.add(imgLabel, BorderLayout.CENTER);
        card.add(bottomInfo, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectProduct(p, card);
            }
        });

        return card;
    }

    private void selectProduct(Product p, JPanel selectedCard) {
        for (JPanel c : cardPanels) {
            c.setBorder(BorderFactory.createLineBorder(new Color(235, 235, 235), 1, true));
        }
        selectedCard.setBorder(BorderFactory.createLineBorder(new Color(100, 150, 255), 2, true));

        nameLabel.setText(p.name);
        priceLabel.setText(p.price);
        brandLabel.setText(p.brand);
        
        java.net.URL imgUrl = getClass().getResource(p.imagePath);
        ImageIcon icon;
        if (imgUrl != null) {
            icon = new ImageIcon(imgUrl);
        } else {
            icon = new ImageIcon("Lab04/" + p.imagePath);
            if (icon.getIconWidth() == -1) {
                icon = new ImageIcon(p.imagePath);
            }
        }
        
        imagePanel.transitionTo(icon.getImage());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Lab4 frame = new Lab4();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}