/*
The MIT License (MIT)

Copyright (c) 2015 Mooxmirror

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package io.mooxmirror.cdoptimizer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CDOptimizer
{
	public static List<CompactDisk> burnAllSongs(List<Song> songs, int size)
	{
		List<CompactDisk> cds = new LinkedList<CompactDisk>();
		
		// Sort by length
		Collections.sort(songs, (Song song1, Song song2) -> Integer.compare(song1.getLength(), song2.getLength()));

		CompactDisk currentCd;
		do
		{
			currentCd = new CompactDisk(size);
			for (int i = songs.size() - 1; i > -1 && !currentCd.limitReached(); i--)
			{
				Song currentSong = songs.get(i);
				
				if (currentCd.canBurn(currentSong))
				{
					currentCd.burn(currentSong);
					songs.remove(currentSong);
				}
			}
			
			if (!currentCd.isEmpty())
				cds.add(currentCd);
			
		} while (!currentCd.isEmpty());
		
		System.out.println(songs.size() + " song(s) not burned.");
		return cds;
	}
	/**
	 * Command line syntax: CDOptimizer [-t] [-d 4800]
	 */
	public static void main(String[] args)
	{
		int compactDiskSize = 4800; // in seconds
		boolean titleMode = false;
		
		for (int i = 0; i < args.length; i++)
		{
			String option = args[i].toLowerCase();
			switch (option)
			{
			case "-s":
				compactDiskSize = Integer.parseInt(args[i+1]);
				break;
			case "-t":
				titleMode = true;
				break;
			}
		}
		
		Scanner input = new Scanner(System.in);
		List<Song> songData = new LinkedList<Song>();
		
		String line = "";
		while (!(line = input.nextLine()).isEmpty())
		{
			int length;
			String title = "";
			
			if (titleMode)
			{
				String[] tokens = line.split("\\s+");
				title = tokens[0];
				length = Integer.parseInt(tokens[1]);
			}
			else
			{
				length = Integer.parseInt(line);
			}
			
			songData.add(new Song(title, length));
		}
		
		input.close();
		
		List<CompactDisk> cds = burnAllSongs(songData, compactDiskSize);
		
		for (int i = 0; i < cds.size(); i++)
		{
			System.out.println("Compact Disk " + (i + 1));
			
			CompactDisk current = cds.get(i);
			System.out.println(current);
		}
	}

}
