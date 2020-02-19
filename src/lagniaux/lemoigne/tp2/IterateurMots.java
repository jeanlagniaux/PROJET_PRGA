package lagniaux.lemoigne.tp2;

import java.util.Iterator;

public class IterateurMots implements Iterator<String> {
	
	protected Object[] o;
	protected int cursor;
	
	public static void main (String[] args) {
		Character[] tab = new Character[8];
		tab[0] = null;
		tab[1] = 'A';
		tab[2] = 'B';
		tab[3] = null;
		tab[4] = null;
		tab[5] = 'C';
		tab[6] = 'D';
		tab[7] = null;
		IterateurMots iter = new IterateurMots(tab) ;
		while(iter.hasNext())
		{
		System.out.print(iter.next() + ", ") ;
		}
	}

	public IterateurMots(Object[] o) {
		this.o = o;
		resetCursor(0);
		
	}
	
	private void resetCursor(int deb) {
		for (int i = deb; i < o.length; i++){
            if (o[i] != null) {
                this.cursor = i;
                break;
            }

            cursor = o.length;
        }
		
	}

	public void remove() {
		throw new UnsupportedOperationException();
	}
	
	
	@Override
	public boolean hasNext() {
		return cursor<=o.length-1; 
	}

	@Override
	public String next() {
		String str = "";
		while(hasNext()) {
			if (o[cursor]!= null) {
				str+=o[cursor]+"";
				cursor++;
			}
			else {
				resetCursor(cursor);
				break;
			}
			
		}
		return str;
	}

}
