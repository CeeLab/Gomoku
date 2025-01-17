package gomoku.gamepanel;
import gomoku.constants.Constants;
import gomoku.gamepanel.DoubleGamePanel.RetractListener;
import gomoku.gamepanel.DoubleGamePanel.StartListener;
import gomoku.gamepanel.GamePanel.AnimationListener;
import gomoku.gamepanel.GamePanel.ExitListener;
import gomoku.gamepanel.GamePanel.GiveupListener;
import gomoku.gamepanel.GamePanel.LastListener;
import gomoku.gamepanel.GamePanel.NextListener;
import gomoku.gamepanel.GamePanel.ReturnListener;
import gomoku.gamepanel.GamePanel.ReviewListener;
import gomoku.language.Language;
import gomoku.sound.Media;
import gomoku.sound.Sound;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
/**
 * 
 * @author luck
 *@version 2013.3.31 23:28
 *人机对战面板
 */
public class SingleGamePanel extends GamePanel {
	private static final long serialVersionUID = 1L;	
	public int RetractNumber;
	public SingleGamePanel(String modelString,int time){
		
		this.time = time;
		jbtExit.addMouseListener(new ExitListener());
		jbtStart.addMouseListener(new StartListener());
		jbtRetract.addMouseListener(new RetractListener());
		jbtGiveup.addMouseListener(new GiveupListener());
		jbtReview.addMouseListener(new ReviewListener());
		jbtReturn.addMouseListener(new ReturnListener());
		jbtNext.addMouseListener(new NextListener());
		jbtLast.addMouseListener(new LastListener());
		jbtAnimation.addMouseListener(new AnimationListener());
		switch (modelString) {
		case "Dark":
			guiboard.addDark();
			break;
		case "Disturb": 
			guiboard.addLong();
			break;
		case "Change":
			guiboard.addChange();
			break;
		default:
			break;
		}
		guiboard.addBG();
	}
	public SingleGamePanel(int time) {
		this.time = time;
		jbtExit.addMouseListener(new ExitListener());
		jbtStart.addMouseListener(new StartListener());
		jbtRetract.addMouseListener(new RetractListener());
		jbtGiveup.addMouseListener(new GiveupListener());
		jbtReview.addMouseListener(new ReviewListener());
		jbtReturn.addMouseListener(new ReturnListener());
		jbtNext.addMouseListener(new NextListener());
		jbtLast.addMouseListener(new LastListener());
		jbtAnimation.addMouseListener(new AnimationListener());	
		
		guiboard.addBG();
	}
	/**
	 * 
	 * @author luck
	 * @version 2013.4.2 15:22
	 * 人机对战的悔棋    
	 *     
	 */
	class RetractListener implements MouseListener {
		int x = jbtRetract.getX();
		int y = jbtRetract.getY();
		@Override
		public void mousePressed(MouseEvent e) {
			jbtRetract.setLocation(x+3,y+3);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			jbtRetract.setLocation(x-2,y-2);
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			jbtRetract.setLocation(x-2,y-2);
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			jbtRetract.setLocation(x,y);
			
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			if (jbtRetract.isEnabled()){
				Media.playSound(Sound.gamechoose);

				if (guiboard.getChessboard().getHistory().size()<= 5){
					return;
				}
				guiboard.unset();
				RetractNumber++;
				if (guiboard.activeplayer == 2) {
					guiboard.removeListener();
				} else {
					guiboard.AddListener();
				}		
			}
			else {
				Media.playSound(Sound.enable);
			}
		}	
	}
	/**
	 * 
	 * @author luck
	 *@version 2013.4.2 15:24
	 * 开始按钮监听
	 */
	class StartListener implements MouseListener {
		
		public void rule(){
			String[] Players = {guiboard.player1.getName(),guiboard.player2.getName()}; 	
			String select = guiboard.player1.choosefirst(Players);
			if (select.equals(guiboard.player2.getName())){
				firstplayer = 2;
				guiboard.changeplayer();
			}			
			new RuleThread().start();
		}
		
		class RuleThread extends Thread {
			public void run() {
			if (guiboard.activeplayer==2){
				while (guiboard.step <= 3) {
					guiboard.player2.play();
				}
			} else {
				while (guiboard.step <= 3) {
					try {
						sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			guiboard.changeplayer();
			selectSwap = (firstplayer ==2?guiboard.player1:guiboard.player2).choose();
			if (selectSwap.equals(Language.CHOOSEWHITE)){
				color_of_Player1= firstplayer==1?Constants.BLACK:Constants.WHITE;
				color_of_Player2= firstplayer==1?Constants.WHITE:Constants.BLACK;
				firstplayer = firstplayer==1?2:1;
				
			}
				else if (selectSwap.equals(Language.DOMORE)) {
					if (guiboard.activeplayer==1) {
						while (guiboard.step<=5){
							try {
								sleep(100);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}		
					} else {
						while (guiboard.step<=5) {
							guiboard.player2.play();
						}
					}
					guiboard.changeplayer();
					selectSwap = (firstplayer ==1?guiboard.player1:guiboard.player2).chooseagain();
					if (selectSwap == Language.CHOOSEBLACK) {
						color_of_Player1= firstplayer==1?Constants.BLACK:Constants.WHITE;
						color_of_Player2= firstplayer==1?Constants.WHITE:Constants.BLACK;
						firstplayer = firstplayer==1?2:1;						
						guiboard.changeplayer();
					} else { 
						color_of_Player1= firstplayer==2?Constants.BLACK:Constants.WHITE;
						color_of_Player2= firstplayer==2?Constants.WHITE:Constants.BLACK;
					}
				} else {
					 color_of_Player1= firstplayer==2?Constants.BLACK:Constants.WHITE;
					color_of_Player2= firstplayer==2?Constants.WHITE:Constants.BLACK;
					guiboard.changeplayer();
				}
			ImageIcon player1Icon= new ImageIcon(color_of_Player1==Constants.BLACK?Language.BLACK:Language.WHITE);
			ImageIcon player2Icon = new ImageIcon(color_of_Player1==Constants.WHITE?Language.BLACK:Language.WHITE);
			jlbPlayer1.setIcon(player1Icon);
			jlbPlayer2.setIcon(player2Icon);
			guiboard.comColor=firstplayer;
			guiboard.playerColor=firstplayer==1?2:1;
			if (guiboard.activeplayer==2) {
				guiboard.player2.play();
				guiboard.changeplayer();
			}
			guiboard.isOK=true;
			jbtGiveup.setEnabled(true);
			new RemindThread().start();
			new CheckWinThread().start();
			while (!guiboard.isWin){
				try {
					sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			jpClock1.setVisible(false);
			jbtReview.setEnabled(true);
			jbtRetract.setEnabled(false);
			jbtGiveup.setEnabled(false);
			}
			
	}

		@Override
		public void mouseClicked(MouseEvent e) {
			if (jbtStart.isEnabled()){
				Media.playSound(Sound.gamechoose);

				guiboard.isEnd=false;
				Reviewpanel.setVisible(false);
				GameTime++;
				guiboard.isWin=true;
				guiboard.activeplayer=1;
				RetractNumber = 0;
				whofirst=0;
				firstplayer=1;
				jlbPlayer1.setIcon(null);
				jlbPlayer2.setIcon(null);
				jpClock1.setVisible(true);
				initialTime();
				jpClock1.setLocation(0,150);
				jbtGiveup.setEnabled(true);
				jbtRetract.setEnabled(true);
				jbtReview.setEnabled(false);
				guiboard.isOK=false;
				guiboard.renew();
				guiboard.AddListener();
				rule();					
				new TimeThread().start();

			}
			else {
				Media.playSound(Sound.enable);
			}
		
		}
		int x = jbtStart.getX();
		int y = jbtStart.getY();
		@Override
		public void mousePressed(MouseEvent e) {
			jbtStart.setLocation(x+3,y+3);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			jbtStart.setLocation(x-2,y-2);
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			jbtStart.setLocation(x-2,y-2);
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			jbtStart.setLocation(x,y);
			
		}
	}

	
}