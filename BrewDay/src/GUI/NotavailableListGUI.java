//package GUI;
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Frame;
//import java.awt.GridLayout;
//import java.awt.TextArea;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
//import javax.swing.BoxLayout;
//import javax.swing.JButton;
//import javax.swing.JFrame;
//import javax.swing.JPanel;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//
//public class NotavailableListGUI extends JFrame{
//	public NotavailableListGUI() {
//		this.setTitle("NotavailableList");
//		this.setSize(400,300);
//		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		JPanel p = new JPanel();
//		
//		this.add(p);
//		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
//		
//		JPanel p1 = new JPanel(); 
//		p1.add(new JTextField("Non-executable recipes",30));
//		p.add(p1);
//		
//		JPanel p2 = new JPanel();
//		p2.setLayout(new FlowLayout(1,10,20));
//		JTextArea t1 = new JTextArea("Recipes missing ingredient 1 400g; missing ingredient 2 400g");
//		//t1.setSize(200, 100);
//		//t1.setSize(200);;
//		p2.add(t1);
//		
//		JTextArea t2 = new JTextArea("Recipes missing ingredient 2 400g");
//		//t2.setSize(200, 100);
//		p2.add(t2);
//		p.add(p2);
//		
//		JPanel p3 = new JPanel();
//		p3.setLayout(new FlowLayout(1,10,10));
//		JButton b1 = new JButton("Back to previous");
//		b1.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				new ResultListGUI().setVisible(true);
//				NotavailableListGUI.this.dispose();
//			}
//		});
//		p3.add(b1);
//		JButton b2 = new JButton("Back to main page");
//		b2.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				new MainpageGUI().setVisible(true);
//				NotavailableListGUI.this.dispose();
//			}
//		});
//		p3.add(b2);
//		p.add(p3);
//		/*
//		p2.setLayout(new GridLayout(2, 2, 20, 10)); 
//		p2.add(new TextArea("Recipes missing ingredient 1 400g; missing ingredient 2 400g"));
//		p2.add(new JButton("Back to previous"));
//		p2.add(new TextArea("Recipes missing ingredient 2 400g"));
//		p2.add(new JButton("Back to main page"));
//		p.add(p2); 
//		
//		JPanel p3 = new JPanel();
//		p.add(p3);
//		*/
//		this.setVisible(true);
//		
//	}
//}
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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.BrewController;
import model.Brew;
import model.Recipe;

public class NotavailableListGUI extends JFrame{
	public NotavailableListGUI(Brew m, BrewController c) {
		this.setTitle("NotavailableList");
		this.setSize(1000,500);
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
		emptyPanel.setPreferredSize(new Dimension(400, 80));
		///////////bg////////////

		JPanel p = new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));

		JPanel p1 = new JPanel();
		p1.setLayout(new FlowLayout(1, 10, 10));
		
		JTextField title = new JTextField("Non-executable recipes",12);
		title.setEditable(false);
		title.setFont(new Font("Verdana",Font.ITALIC,15));
		title.setBorder(BorderFactory.createEmptyBorder());
		title.setOpaque(false);

		p1.add(title);
		p.add(p1);

		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(m.getnotRecommendedRecipeIndex().size(), 3, 20, 10)); 


		for(Recipe r:m.getnotRecommendedRecipeIndex())//for all recipe that is non-executable
		{
			JTextField ARecipe = new JTextField(r.getName()+"  "+String.valueOf(r.getLackAmount()+" L"),30);
			ARecipe.setEditable(false);
			p2.add(ARecipe);

			JButton AButton = new JButton("shoping list");
			AButton.setFont(new Font("Verdana",Font.ITALIC,15));
			AButton.setContentAreaFilled(false);
			
			AButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					//when click, jump to shopping list page
					m.setshopindex(r.getRecipeIndex());

					m.getView().get(3).setvisible(0);

					m.getView().get(4).setvisible(1);
					try {
						m.notifyView();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					NotavailableListGUI.this.dispose();
				}
			});
			p2.add(AButton);
		}



		//p.add(p2); 

		JPanel p3 = new JPanel();
		p3.setLayout(new FlowLayout(1,1,5));
		JButton b3 = new JButton("Back to previvous");
		b3.setFont(new Font("Verdana",Font.ITALIC,15));
		b3.setContentAreaFilled(false);
		
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {								
				m.getView().get(3).setvisible(0);				
				m.getView().get(1).setvisible(1);
				try {
					m.notifyView();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				NotavailableListGUI.this.dispose();
			}
		});
		p3.add(b3);
		p.add(p3);
		p.add(p2);
		
		bg.add(emptyPanel);
		bg.add(p);
		
		emptyPanel.setOpaque(false);
		p3.setOpaque(false);
		p2.setOpaque(false);
		p1.setOpaque(false);
		p.setOpaque(false);
		this.add(bg);

	}

	public void controlVisible(int flag) {
		if(flag==1)
		{
			this.setVisible(true);
		}
		else {
			System.out.print(this.getClass());
			this.setVisible(false);
		}

	}

}
