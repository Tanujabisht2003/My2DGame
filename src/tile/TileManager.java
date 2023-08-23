package tile;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {

	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][][];
	boolean drawPath = true;
	
	
	public TileManager(GamePanel gp) {
		
		this.gp = gp;
		
		tile = new Tile[50];
		mapTileNum = new int[gp.maxMap][gp.maxworldCol][gp.maxworldRow];
		
		getTileImage();
		loadMap("/maps/worldV3.txt", 0);
		loadMap("/maps/interior01.txt",1);
	}
	
	
	public void getTileImage() {
		//PLACEHOLDER
		Setup(0, "grass00", false);
		Setup(1, "grass00", false);
		Setup(2, "grass00", false);
		Setup(3, "grass00", false);
		Setup(4, "grass00", false);
		Setup(5, "grass00", false);
		Setup(6, "grass00", false);
		Setup(7, "grass00", false);
		Setup(8, "grass00", false);
		Setup(9, "grass00", false);
		
		//PLACEHOLDER
		Setup(10,"grass00", false);
		Setup(11,"grass01", false);
		Setup(12, "water00", true);
		Setup(13, "water01", true);
		Setup(14, "water02", true);
		Setup(15, "water03", true);
		Setup(16, "water04", true);
		Setup(17, "water05", true);
		Setup(18, "water06", true);
		Setup(19, "water07", true);
		Setup(20, "water08", true);
		Setup(21, "water09", true);
		Setup(22, "water10", true);
		Setup(23, "water11", true);
		Setup(24, "water12", true);
		Setup(25, "water13", true);
		Setup(26, "road00", false);
		Setup(27, "road01", false);
		Setup(28, "road02", false);
		Setup(29, "road03", false);
		Setup(30, "road04", false);
		Setup(31, "road05", false);
		Setup(32, "road06", false);
		Setup(33, "road07", false);
		Setup(34, "road08", false);
		Setup(35, "road09", false);
		Setup(36, "road10", false);
		Setup(37, "road11", false);
		Setup(38, "road12", false);
		Setup(39, "earth", false);
		Setup(40, "wall", true);
		Setup(41, "tree", true);
		Setup(42, "hut", false);
		Setup(43, "floor01", false);
		Setup(44, "table01", true);
	}


	public void Setup(int index, String imageName, boolean collision) {
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/"+ imageName + ".png"));
		    tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
			
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath, int map) {
		
		try {
			
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col =0;
			int row= 0;
			
			while(col< gp.maxworldCol && row< gp.maxworldRow) {
				
				String line = br.readLine();
				
				while(col< gp.maxworldCol ){
					
					String numbers[] = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[map][col][row] = num;
					col++;
					
				}
				if(col == gp.maxworldCol) {
					col = 0;
					row++;
				}
				
			}
			br.close();
		}catch(Exception e) {
			
		}
	}
	
	
	
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		
		while(worldCol < gp.maxworldCol && worldRow < gp.maxworldRow) {
			
			int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX && 
			   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX && 
			   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY && 
			   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			
		    worldCol++;
		   
		    
		    if(worldCol == gp.maxworldCol) { 
		    	worldCol = 0;
		    	worldRow++;
		    	
		    	
		    	}
		    }
		    if(drawPath == true) {
		    	g2.setColor(new Color(255,0,0,70));
		    	
		    	for(int i =0;i < gp.pFinder.pathList.size();i++) {
		    		
		    		int worldX = gp.pFinder.pathList.get(i).col * gp.tileSize;
		    		int worldY = gp.pFinder.pathList.get(i).row * gp.tileSize;
		    		int screenX = worldX - gp.player.worldX + gp.player.screenX;
		    		int screenY = worldY - gp.player.worldY + gp.player.screenY;
		    		
		    		g2.fillRect(screenX, screenY, gp.tileSize, gp.tileSize);
		    	}
		    }
		}
		
}
