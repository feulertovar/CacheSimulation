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

import java.util.Scanner;

//Cache Simulation Class
public class Cache {

	// declare simulated cache
	private static Integer[] Main_mem = new Integer[2048];
	private static CacheModel[] Main_cache = new CacheModel[16];

	private Scanner keyboard = new Scanner(System.in);

	public Cache() {
		// TODO Auto-generated constructor stub
	}

	public CacheModel disassemble(int Address, CacheModel slot) {

		return slot;
	}

	// initialize the memory array
	public void initMem() {

		// init simulated cache
		for (Integer i = 0; i < Main_mem.length; i++) {
			Main_mem[i] = (i & BitMask.MEMINIT.getMask());
		}

	}

	public void initCache() {

		// init simulated cache
		for (int i = 0; i < Main_cache.length; i++) {

			Main_cache[i] = new CacheModel();
			// init slot numbers
			Main_cache[i].setSlot(i);
			// init data
			for (Integer j = 0; j < Main_cache[i].getData().length; j++) {
				Main_cache[i].getData()[j] = 0;
			}
		}
	}

	public void readAddress(int address) {

		CacheModel mod = new CacheModel();
		int firstByte, tag, value;
		String hitmiss = "";

		// breakdown address into tag, slot# and offset
		mod.setOffset(address & BitMask.OFFSET.getMask());
		mod.setSlot((address & BitMask.SLOT.getMask()) >>> 4);
		mod.setTag((address) >>> 8);

		// check dirty bit + if we have a hit or miss.
		// determine if we should write back to memory
		// before updating the cache
		if (Main_cache[mod.getSlot()].getDirty() == 1) {

			// goto slot# to see if the tag matches and valid bit = 1 (Cache Hit)
			// return byte value at address from cache
			if ((Main_cache[mod.getSlot()].getTag() == mod.getTag()) & Main_cache[mod.getSlot()].getValid() == 1) {

				hitmiss = "(Cache Hit)";

			} else {
				// if (Cache Miss), goto memory, copy from beginning of block (offset = 0) to
				// end into slot#.
				hitmiss = "(Cache Miss)";

				// write block to memory first
				writeBlockToMem(Main_cache[mod.getSlot()].getSlot());
				System.out.println("wrote block " + Main_cache[mod.getSlot()].getTag() + mod.getSlot() + "0 to memory");

				// copy new block to cache
				firstByte = mod.getSlot() << 4;
				tag = mod.getTag() << 8;
				copyBlockToCache(mod.getSlot(), firstByte, tag);

				// Set valid = 1 and update Tag.
				Main_cache[mod.getSlot()].setTag(mod.getTag());
				Main_cache[mod.getSlot()].setValid(1);

				// reset dirty bit
				Main_cache[mod.getSlot()].setDirty(0);

			}

		}
		// Dirty Bit
		else {
			// goto slot# to see if the tag matches and valid bit = 1 (Cache Hit)
			// just return byte value at address from cache
			if ((Main_cache[mod.getSlot()].getTag() == mod.getTag()) & Main_cache[mod.getSlot()].getValid() == 1) {

				hitmiss = "(Cache Hit)";

			} else {
				// if (Cache Miss), goto memory, copy from beginning of block (offset = 0) to
				// end into slot#.
				hitmiss = "(Cache Miss)";

				// copy new block to cache
				firstByte = mod.getSlot() << 4;
				tag = mod.getTag() << 8;
				copyBlockToCache(mod.getSlot(), firstByte, tag);

				// Set valid = 1 and update Tag.
				Main_cache[mod.getSlot()].setTag(mod.getTag());
				Main_cache[mod.getSlot()].setValid(1);

			}

		}
		// in cache (slot#), use block offset to index into block and return the proper
		// byte
		value = readByte(mod.getSlot(), mod.getOffset());
		System.out.println("At address 0x" + Integer.toHexString(address) + " byte value = 0x"
				+ Integer.toHexString(value) + ", " + hitmiss);

	}

	public void writeAddress(int address, int ibyte) {

		// breakdown address into tag, slot# and offset
		CacheModel mod = new CacheModel();
		int firstByte, tag, value;
		String hitmiss = null;

		mod.setOffset(address & BitMask.OFFSET.getMask());
		mod.setSlot((address & BitMask.SLOT.getMask()) >>> 4);
		mod.setTag((address) >>> 8);

		// check dirty bit to determine if we should write back to memory
		// before updating the cache
		if (Main_cache[mod.getSlot()].getDirty() == 1) {

			// goto slot# to see if the tag matches and valid bit = 1 (Cache Hit)
			// return byte value at address from cache
			if ((Main_cache[mod.getSlot()].getTag() == mod.getTag()) & Main_cache[mod.getSlot()].getValid() == 1) {
				hitmiss = "(Cache Hit)";

				// just set dirty bit to 1
				// Block is already in cache,
				// add new byte data to address
				writeByteToCache(mod.getOffset(), ibyte, mod.getSlot());
				Main_cache[mod.getSlot()].setDirty(1);

			} else {

				hitmiss = "(Cache Miss)";

				// write block to memory first
				writeBlockToMem(Main_cache[mod.getSlot()].getSlot());
				System.out.println("wrote block " + Main_cache[mod.getSlot()].getTag() + mod.getSlot() + "0 to memory");

				// copy new block to cache
				firstByte = mod.getSlot() << 4;
				tag = mod.getTag() << 8;
				copyBlockToCache(mod.getSlot(), firstByte, tag);

				// just set dirty bit to 1
				// Block is already in cache, add new byte data to address
				writeByteToCache(mod.getOffset(), ibyte, mod.getSlot());
				Main_cache[mod.getSlot()].setDirty(1);
			}

		}
		// Dirty Bit is zero
		else {

			// goto slot# to see if the tag matches and valid bit = 1 (Cache Hit)
			// return byte value at address from cache
			if ((Main_cache[mod.getSlot()].getTag() == mod.getTag()) & Main_cache[mod.getSlot()].getValid() == 1) {
				hitmiss = "(Cache Hit)";

				// Write only into the cache
				// Set dirty but to 1
				writeByteToCache(mod.getOffset(), ibyte, mod.getSlot());
				Main_cache[mod.getSlot()].setDirty(1);

			} else {

				// if (Cache Miss), goto memory, copy from beginning of block (offset\ = 0) to
				// end into slot#.
				hitmiss = "(Cache Miss)";

				// copy new block to cache
				firstByte = mod.getSlot() << 4;
				tag = mod.getTag() << 8;
				copyBlockToCache(mod.getSlot(), firstByte, tag);

				// Set valid = 1 and update Tag.
				Main_cache[mod.getSlot()].setTag(mod.getTag());
				Main_cache[mod.getSlot()].setValid(1);

				// Add new byte data to address
				writeByteToCache(mod.getOffset(), ibyte, mod.getSlot());

				// set dirty bit
				Main_cache[mod.getSlot()].setDirty(1);

			}
		}
		// in cache (slot#), use block offset to index into block and return the proper
		// byte
		value = readByte(mod.getSlot(), mod.getOffset());
		System.out.println("At address 0x" + Integer.toHexString(address) + " byte value = 0x"
				+ Integer.toHexString(value) + ", " + hitmiss);

	}

	// read a particular byte
	public int readByte(int slot, int offset) {

		int iByte;

		iByte = Main_cache[slot].getData()[offset];

		return iByte;
	}

	// write a byte to cache
	public void writeByteToCache(int offset, int ibyte, int slot) {

		// Main_mem[address] = ibyte;
		Main_cache[slot].setData(ibyte, offset);

	}

	// copy a block from memory to cache
	public void copyBlockToCache(int slot, int firstByte, int tag) {

		for (Integer j = 0; j < Main_cache[slot].getData().length; j++) {
			Main_cache[slot].setData(Main_mem[tag + firstByte + j], j);
		}
	}

	// write to memory if we have a dirty bit
	public void writeBlockToMem(int slot) {
		int blockaddress = (Main_cache[slot].getTag() << 8 | slot << 4);

		for (int j = 0; j < Main_cache[slot].getData().length; j++) {
			// combine tag, slot# and provide offset to write block to address
			int data = Main_cache[slot].getData()[j];
			Main_mem[blockaddress] = data;

			// increase address offset
			blockaddress += 1;
		}
	}

	// display the cache
	public void displayCache() {

		String slot = "Slot";
		String valid = "Valid";
		String tag = "Tag";
		String dirty = "Dirty";
		String data = "Data";

		System.out.println();
		System.out.format("%7s%7s%5s%6s%8s", valid, dirty, tag, slot, data);
		System.out.println();

		for (Integer i = 0; i < Main_cache.length; i++) {
			System.out.format("%7X%5X%5X%5X   ", Main_cache[i].getValid(), Main_cache[i].getDirty(),
					Main_cache[i].getTag(), Main_cache[i].getSlot());
			for (Integer j = 0; j < Main_cache[i].getData().length; j++) {
				System.out.format("%5X", Main_cache[i].getData()[j]);
			}
			// System.out.println(Main_cache[i]);
			System.out.println(" ");
		}
		System.out.println();
	}

	// display all of memory
	public void displayMem() {

		for (Integer i = 0; i < Main_mem.length; i++) {
			System.out.println(Integer.toHexString(Main_mem[i]));
		}
	}

	/**
	 * filer each word for non a-z or 0-9 characters
	 */
	public String filterInput(String str) {

		// filter letter and numbers
		str = str.replaceAll("[^a-zA-Z0-9]", "");

		return str;
	}

	// Get user input and check that user input integer characters
	public void getInput() {

		String myInput = "";

		do {
			try {
				System.out.println("");
				System.out.println("(R)ead, (W)rite, or (D)isplay (X to exit)?");
				myInput = keyboard.next();

				// remove all alpha characters
				myInput = filterInput(myInput);

				// check if a alpha remains, else throw exception
				if (myInput.equalsIgnoreCase("R") | myInput.equalsIgnoreCase("W") | myInput.equalsIgnoreCase("D") | myInput.equalsIgnoreCase("X") ){

					// switch statement
					switch (myInput) {
					case "R":
					case "r": {
						System.out.println("At what address would you like to read?");
						int add  = keyboard.nextInt(16);
						readAddress(add);
					}
					break;
					case "W":
					case "w": {
						System.out.println("At what address would you like to write?");
						int add = keyboard.nextInt(16);

						System.out.println("What value would you like to write?");
						int val =keyboard.nextInt(16);
						writeAddress(add, val);
					}
						break;
					case "D":
					case "d": {
						displayCache();
					} 
					break;
					case "X": {
						exit();
					}
						break;
					default:
						break;
					}

				} else {
					throw new Exception("Please enter an R, W, D or X to exit");

				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} while (!myInput.equals("X"));

	}

	// Get user input and check int user input integer characters
	public int checkNumberInput(String myInput) {

		int myNumber = 0;

		do {
			try {
				// remove all non-numeric characters
				myInput = myInput.replaceAll("[^a-zA-Z0-9]", "");

				// check if a number remains, else throw exception
				if (!myInput.equals("")) {
					myNumber = Integer.parseInt(myInput);
				} else {
					throw new Exception("Please enter an interger or Hex digit");
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

		} while (myInput.equals(""));

		return myNumber;

	}
	
	public void exit() {
		
		System.exit(0);
	}

}
