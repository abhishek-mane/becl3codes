import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;
import java.io.*;
import java.util.Random;

class QuickSort
{
	static int choosePivot(int []arr, int low, int high)
	{
		int mid = (low+high)/2, temp;
	
		if(arr[low] > arr[high])
		{
			temp = low; low = high; high = temp;
		}

		if(arr[mid] < arr[low])
		{
			temp = mid; mid = low; low = temp;
		}

		if(arr[high] < arr[mid])
		{
			temp = mid; mid = high; high = temp;
		}

		return mid;
	}
	
	static void swap(int []arr, int x, int y)
	{
		int temp;
		temp = arr[x]; arr[x] = arr[y]; arr[y] = temp;
	}
	
	static int partition(int []arr, int low, int high)
	{
		int i, pivotIdx = choosePivot(arr, low, high), pivotVal = arr[pivotIdx];

		swap(arr, pivotIdx, high);
		int storeIdx = low;

		for(i = low; i < high; i++)
		{
			if(arr[i] < pivotVal)
			{
				swap(arr, i, storeIdx);
				storeIdx++;
			}
		}
		
		swap(arr, storeIdx, high);
		return storeIdx;
	}
	
	static void sort(int []arr, int low, int high)
	{
		if(low < high)
		{
			int p = partition(arr, low, high);
			
			
			if(QuickSortParallel.MIN_SIZE != (high-low+1))
			{
				sort(arr, low, p-1);
				sort(arr, p+1, high);
			}else{
				System.out.println("Hello World !");
				QuickSortTask t1 = new QuickSortTask(arr, low, p-1);
				t1.start();
				
				QuickSort.sort(arr, p+1, high);
				
				try{
					t1.join();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}

class QuickSortTask extends Thread
{
	int []arr;
	int low;
	int high;
	
	QuickSortTask(int []arr, int low, int high)
	{
		this.arr = arr;
		this.low = low;
		this.high = high;
	}
	
	@Override
	public void run()
	{
		QuickSort.sort(arr, low, high);
	}
	
}

class QuickSortParallel
{
	static int MIN_SIZE;
	
	@SuppressWarnings("deprecation")
	public static void main(String args[]) throws Exception
	{
		/*******************************
		 Read input from XML 
		********************************/
		File inputFile = new File("numbers.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();
		
		NodeList size = doc.getElementsByTagName("size");
		int NO_OF_ELEMENT = Integer.parseInt(size.item(0).getTextContent());
		
		int [] arr = new int [NO_OF_ELEMENT];
		
		NodeList nodeArray = doc.getElementsByTagName("number");
		
		for(int i=0; i<NO_OF_ELEMENT; ++i)
		{
			arr[i] = Integer.parseInt(nodeArray.item(i).getTextContent());
		}
		/*End reading input*/
		
		MIN_SIZE = arr.length;
		
		long start = System.currentTimeMillis();
		QuickSort.sort(arr, 0, arr.length-1);
		long end = System.currentTimeMillis();
		long diff = end-start;
		
		PrintWriter pw = new PrintWriter(new File("output.txt"));
		pw.println("Time taken : " + diff +" MilliSeconds");
		pw.println("\nSorted Numbers: ");
			
		for(int i=0; i<arr.length; ++i)
			pw.println(arr[i]);
			
		pw.close();
		System.out.println("Output is successfully stored in \"output.txt\"");
	}
}
