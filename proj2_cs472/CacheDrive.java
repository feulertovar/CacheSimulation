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

public class CacheDrive {

	public CacheDrive() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		CacheDrive me = new CacheDrive();
		me.doIt();
	}

	public void doIt() {

		System.out.println("***********************Cache Simulation************************");
		System.out.println("*                                                             *");
		System.out.println("* This program is a softwware simulation of cache memory      *");
		System.out.println("* Implementation will that of direct-mapped, Write-back cache *");
		System.out.println("*                                                             *");
		System.out.println("***************************************************************");
		System.out.println("");

		Cache dis = new Cache();
		dis.initMem();
		dis.initCache();
		
		//Run the programm in automatically or in manual mode
		dis.getInput();
		//runTestScript();

	}

	// Test script to generate output
	public void runTestScript() {
		Cache dis = new Cache();
		dis.initMem();
		dis.initCache();

		dis.readAddress(0x5);
		dis.readAddress(0x6);
		dis.readAddress(0x7);
		dis.readAddress(0x14C);
		dis.readAddress(0x14D);
		dis.readAddress(0x14E);
		dis.readAddress(0x14F);
		dis.readAddress(0x150);
		dis.readAddress(0x151);
		dis.readAddress(0x3A6);
		dis.readAddress(0x4C3);

		dis.displayCache();

		dis.writeAddress(0x14C, 0x99);
		dis.writeAddress(0x63B, 0x07);
		dis.readAddress(0x582);

		dis.displayCache();

		dis.readAddress(0x348);
		dis.readAddress(0x3F);

		dis.displayCache();

		dis.readAddress(0x14B);
		dis.readAddress(0x14C);
		dis.readAddress(0x63F);
		dis.readAddress(0x83);

		dis.displayCache();

	}

}
