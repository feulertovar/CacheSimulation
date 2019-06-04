/**
* <h1>Cache Simulation</h1>
* This program is a softwware simulation of cache memory
* Implementation will that of direct-mapped, Write-back cache.
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
public class CacheModel {

	/**
	 * 
	 */
	public CacheModel() {
		// TODO Auto-generated constructor stub
	}
	private int valid = 0;
	private int dirty = 0;
	private int tag = 0; 
	private int slot = 0;
	private int offset = 0;
	private Integer[] data = new Integer[16];
	
	public Integer[] getData() {
		return data;
	}
	public void setData(int ibyte, int offset) {
		this.data[offset] = ibyte;
	}
	public int getValid() {
		return valid;
	}
	public void setValid(int valid) {
		this.valid = valid;
	}

	public int getDirty() {
		return dirty;
	}
	public void setDirty(int dirty) {
		this.dirty = dirty;
	}
	public int getTag() {
		return tag;
	}
	public void setTag(int tag) {
		this.tag = tag;
	}
	public int getSlot() {
		return slot;
	}
	public void setSlot(int slot) {
		this.slot = slot;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	

}
