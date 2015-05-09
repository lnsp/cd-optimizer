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

import java.util.LinkedList;
import java.util.List;

public class CompactDisk
{
	private final int maxSize;
	
	private List<Song> burnedSongs;
	private int length;
	
	public CompactDisk(int maximumSize)
	{
		burnedSongs = new LinkedList<Song>();
		maxSize = maximumSize;
		length = 0;
	}
	
	public void burn(Song song)
	{
		if (song.getLength() + length > maxSize)
		{
			System.err.println("Failed to burn song " + song.toString());
			return;
		}
		
		length += song.getLength();
		burnedSongs.add(song);
	}
	
	public boolean canBurn(Song song)
	{
		if (song.getLength() + length <= maxSize)
			return true;
		
		return false;
	}
	
	public boolean limitReached()
	{
		return length == maxSize;
	}
	
	public List<Song> getAllSongs()
	{
		return new LinkedList<Song>(burnedSongs);
	}
	
	public int getLength()
	{
		return length;
	}
	public boolean isEmpty()
	{
		return length == 0;
	}
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Length: " + length + " / " + maxSize);
		builder.append('\n');
		
		for (Song song : getAllSongs())
			builder.append(song.toString());
		
		return builder.toString();
	}
}
