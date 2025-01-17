package gomoku.main.guiboard;

import java.util.ArrayList;

import gomoku.chesshandle.CheckWin;
import gomoku.chesshandle.ChessBoard;
import gomoku.constants.Constants;
import gomoku.theme.Theme;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * 
 * @author luck NorviNS����
 *	@version 2013.4.1.1:00
 *  ���̲��� UI
 */
public class GuiBoard extends JPanel{
	private static final long serialVersionUID = 1L;
	protected JButton buttonList[][]= new JButton [Constants.SIZE][Constants.SIZE];
	public int flag[][] = new int[Constants.SIZE][Constants.SIZE] ;
	protected ChessBoard chessboard = new ChessBoard();
	protected CheckWin checkWin = new CheckWin();
	public boolean isEnd=false;
	public boolean isPass=false;
	public int color = 1;
	public int step = 1;
	public int activeplayer=1;
	public int nextplayer=2;
	public boolean isWin = false;
	public boolean Retractable = false;
	public int comColor;
	public int playerColor;
	public int CpuNumber;

	public GuiBoard() {
		this.setOpaque(false);
		this.setLayout(null);
		
	}
	public void addBG(){
		makeButtons();
		ImageIcon guiBg = new ImageIcon(Theme.guibg);
		JLabel guiBgLabel = new JLabel(guiBg);
		guiBgLabel.setBounds(-102,-27,guiBg.getIconWidth(),guiBg.getIconHeight());
		this.add(guiBgLabel);	
	
	}
	/**
	 * new ��ť
	 */
	public void makeButtons() {
		int x,y;
		int i=0;
		int j=0;
		for (x=15;x<Constants.BOARDSIZE;x=x+Constants.CHESSSIZE) {
			j=0;
			for (y=15;y<Constants.BOARDSIZE;y=y+Constants.CHESSSIZE) {
				buttonList[i][j] = new JButton();
				buttonList[i][j].setSize(Constants.CHESSSIZE,Constants.CHESSSIZE);
				buttonList[i][j].setLocation(x,y);				
				buttonList[i][j].setContentAreaFilled(false);
				buttonList[i][j].setBorderPainted(false);
				flag[i][j]=0;
				this.add(buttonList[i][j]);							
				j++;
			}
			i++;
		}
	}

	public void refresh() {
		for (int i=0;i<15;i++) {
			for (int j=0;j<15;j++) {
				buttonList[i][j].setIcon(null);
				flag[i][j]=0;
			}
		}
	}
	/**
	 * ������������
	 */
	public void renew() {
		chessboard= new ChessBoard();
		refresh();
		color=1;
		activeplayer=1;
		nextplayer=2;
		step=1;
		isWin=false;
	}

	/**
	 * 
	 * @param temp
	 */
	public void removeIcon(Integer[] temp) {
		buttonList[temp[0]][temp[1]].setIcon(null);
	}
	/**
	 * 
	 * @return
	 */
	public ArrayList<Integer[]> getHistory() {
		return chessboard.getHistory();
	}
	/**
	 * 
	 * @param temp
	 */
	public void SetIcon(Integer[] temp) {
		buttonList[temp[0]][temp[1]].setIcon(new ImageIcon( temp[2]==Constants.BLACK? Theme.black:Theme.white));
		flag[temp[0]][temp[1]]=1;
	}
	public ChessBoard getChessboard() {
		return chessboard;
	}
}
