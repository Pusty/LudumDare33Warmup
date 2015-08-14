package me.engine.world;

public class Chunk {
	int[] idarray;
	int[] metaarray;
	int[] lightarray;
	int sizex;
	int sizez;
	int chunkx;
	int chunkz;
		public Chunk(int cx,int cz,int sx,int sz){
			idarray = new int[sx * sz];
			metaarray = new int[sx * sz];
			lightarray = new int[sx * sz];
			sizex = sx;
			sizez = sz;
			chunkx=cx;
			chunkz=cz;
			for(int i=0;i<idarray.length;i++){
				idarray[i]=0;
				metaarray[i]=0;
				lightarray[i]=0;
			}
			
		}
		public int getChunkX(){return chunkx;}
		public int getChunkZ(){return chunkz;}
		public int getBlockID(int x, int z) {
			if (x >= sizex || z >= sizez || x < 0 || z < 0)
				return 0;
			return idarray[z * sizex + x];
		}
		
		public int getBlockMeta(int x, int z) {
			if (x >= sizex || z >= sizez || x < 0 || z < 0)
				return 0;
			return metaarray[z * sizex + x];
		}
		
		public int getBlockLight(int x, int z) {
			if (x >= sizex || z >= sizez || x < 0 || z < 0)
				return 0;
			return lightarray[z * sizex + x];
		}

		public int getSizeX() {
			return sizex;
		}

		public int getSizeZ() {
			return sizez;
		}

		public int[] getBlockArray() {
			return idarray;
		}
		public void setBlockID(int x, int z,int id) {
			if (x >= sizex || z >= sizez || x < 0 || z < 0)
				return;
			idarray[z * sizex + x]=id;
		}
		
		public void setBlockMeta(int x, int z,int meta) {
			if (x >= sizex || z >= sizez || x < 0 || z < 0)
				return;
			metaarray[z * sizex + x]=meta;
		}
		
		public void setBlockLight(int x, int z,int light) {
			if (x >= sizex || z >= sizez || x < 0 || z < 0)
				return;
			lightarray[z * sizex + x]=light;
		}
}
