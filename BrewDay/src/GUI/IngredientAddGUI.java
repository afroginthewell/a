package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Daoiml.storageingredientDaoiml;
import controller.StorageIngredientController;
import model.StorageIngredient;

public class IngredientAddGUI extends JFrame {

	public IngredientAddGUI(ArrayList<StorageIngredient> sIngredientList, StorageIngredientController c,
			StorageIngredient m) {

		this.setTitle("Ingredient add page");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		///////////bg////////////
		JPanel bg = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				ImageIcon ii = new ImageIcon( "C:\\Users\\Jerry Zou\\Desktop\\JieLi\\Java workspace\\20190511\\brew.jpg");
				g.drawImage(ii.getImage(), 0, 0, getWidth(), getHeight(), ii.getImageObserver());
			}
		};

		JPanel emptyPanel = new JPanel();
		emptyPanel.setLayout(new FlowLayout(1,10,10));
		emptyPanel.setPreferredSize(new Dimension(400, 120));
		///////////bg////////////
		
	
		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
		
		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(2, 2, 20, 10)); 
		Dimension textSize = new Dimension(100,40);
		
		// First text field
		// Indicate the use what they want todo
		JTextArea addIngredient = new JTextArea("Which ingredient you want to add?");
		addIngredient.setFont(new Font("Verdana",Font.ITALIC,13));
		addIngredient.setBorder(BorderFactory.createEmptyBorder());
		addIngredient.setOpaque(false);
		JTextArea inputAddIngredient = new JTextArea("");
		inputAddIngredient.setPreferredSize(textSize);
		inputAddIngredient.setFont(new Font("Verdana",Font.ITALIC,15));
		inputAddIngredient.setBorder(BorderFactory.createEmptyBorder());
		
		// Add the ingredient text field
		p1.add(addIngredient);
		p1.add(inputAddIngredient);
		
		// Use text field as the guidline
		JTextArea addAmount = new JTextArea("How much you want to add?");
		addAmount.setPreferredSize(textSize);
		addAmount.setPreferredSize(textSize);
		addAmount.setFont(new Font("Verdana",Font.ITALIC,13));
		addAmount.setBorder(BorderFactory.createEmptyBorder());
		addAmount.setOpaque(false);
		
		JTextArea inputaddAmount = new JTextArea("");
		inputaddAmount .setFont(new Font("Verdana",Font.ITALIC,15));
		inputaddAmount .setBorder(BorderFactory.createEmptyBorder());
		
		// Add component to page
		p1.add(addAmount);
		p1.add(inputaddAmount );
		
		JPanel p2 = new JPanel();
		p2.setLayout(new FlowLayout(1, 10, 10));
		JButton b1 = new JButton("Add");
		b1.setContentAreaFilled(false);
		b1.setFont(new Font("Verdana",Font.ITALIC,15));
		b1.setOpaque(false);
		
		// Set the listener
		b1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
						
					// Get the info. from input
					String name = inputAddIngredient.getText().toString();
					String amount = inputaddAmount.getText().toString();
					
					// Flage to identify the invalid input
					Double Index=-100.0;
					try {
						// Error input
					Index = Double.parseDouble(amount);
					} catch (Exception exception) {
					JOptionPane.showMessageDialog(null, "Invaild input!!!");
					IngredientAddGUI.this.dispose();
					m.getView().get(2).setvisible(0);
					IngredientAddGUI.this.dispose();
					m.getView().get(0).setvisible(1);
				
				}
				
				
				if (Index < 0) {
					if(Index!=-100.0)
					{
						// Numeriacla error input
					JOptionPane.showMessageDialog(null, "input should be bigger then 0!!!");
					IngredientAddGUI.this.dispose();
					m.getView().get(2).setvisible(0);
					IngredientAddGUI.this.dispose();
					m.getView().get(0).setvisible(1);
					}
				}
				
				 else {
					
					
					try {
						c.addNewIngredient(name, Index);
					} catch (SQLException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					
					// Alert successful information
					JOptionPane.showMessageDialog(null, "Add Sucessfully!");
					// Jump back to IngredientMaintain page
					m.getView().get(2).setvisible(0);
					IngredientAddGUI.this.dispose();
					m.getView().get(0).setvisible(1);
					
				 }
					try {
						m.notifyView();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}

			}
		);
		// Set cancel operations
		JButton b2 = new JButton("Cancel");
		b2.setContentAreaFilled(false);
		b2.setFont(new Font("Verdana",Font.ITALIC,15));
		b2.setOpaque(false);
		
		// corrdinate the page visibility in the system
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				m.getView().get(0).setvisible(1);
				
				m.getView().get(2).setvisible(0);
				try {
					m.notifyView();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				IngredientAddGUI.this.dispose();
			}
		});
		// Add component
		p2.add(b1);
		p2.add(b2);
		p.add(p1);
		p.add(p2);
		
		bg.add(emptyPanel);
		bg.add(p);
		this.add(bg);
		
		emptyPanel.setOpaque(false);
		p1.setOpaque(false);
		p2.setOpaque(false);
		p.setOpaque(false);
		
		IngredientAddGUI.this.setAlwaysOnTop(true);
	}
	
	// Invere visibility
	public void controlVisible(int flag) {
		if (flag == 1) {
			this.setVisible(true);
		} else {
			this.setVisible(false);
		}

	}
}
