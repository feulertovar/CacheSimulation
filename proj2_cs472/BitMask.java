/**
* <h1>Cache Simulation</h1>
* This program is a softwware simulation of cache memory.
* Implementation will be that of a direct-mapped, Write-back cache.
* <p>
* 
*
* @author  Feuler Tovar
* @version 1.0
* @since   20194-03-17 
*/

package proj2_cs472;

/**
 * @author feuler
 *
 */
//Enum to hold Bitmask for Tag, Slot # and block offset.
public enum BitMask {
	
	TAG(0xFFFFFF00),SLOT(0x000000F0), OFFSET(0x0000000F), MEMINIT(0x0FF),  BLOCKINIT(0xFF0);
	private int mask;

	public int getMask() {
		return mask;
	}

	public void setMask(int mask) {
		this.mask = mask;
	}

	private BitMask(int mask) {
		this.mask = mask;
	}
}
