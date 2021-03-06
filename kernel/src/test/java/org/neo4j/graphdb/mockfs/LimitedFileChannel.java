/**
 * Copyright (c) 2002-2012 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.neo4j.graphdb.mockfs;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class LimitedFileChannel extends FileChannel
{

    private final FileChannel inner;
    private LimitedFilesystemAbstraction fs;

    public LimitedFileChannel( FileChannel inner, LimitedFilesystemAbstraction limitedFilesystemAbstraction )
    {
        this.inner = inner;
        fs = limitedFilesystemAbstraction;
    }

    @Override
    public int read( ByteBuffer byteBuffer ) throws IOException
    {
        return inner.read( byteBuffer );
    }

    @Override
    public long read( ByteBuffer[] byteBuffers, int i, int i1 ) throws IOException
    {
        return inner.read( byteBuffers, i, i1 );
    }

    @Override
    public int write( ByteBuffer byteBuffer ) throws IOException
    {
        fs.ensureHasSpace();
        return inner.write( byteBuffer );
    }

    @Override
    public long write( ByteBuffer[] byteBuffers, int i, int i1 ) throws IOException
    {
        fs.ensureHasSpace();
        return inner.write( byteBuffers, i, i1 );
    }

    @Override
    public long position() throws IOException
    {
        return inner.position();
    }

    @Override
    public FileChannel position( long l ) throws IOException
    {
        return inner.position( l );
    }

    @Override
    public long size() throws IOException
    {
        return inner.size();
    }

    @Override
    public FileChannel truncate( long l ) throws IOException
    {
        return inner.truncate( l );
    }

    @Override
    public void force( boolean b ) throws IOException
    {
        fs.ensureHasSpace();
        inner.force( b );
    }

    @Override
    public long transferTo( long l, long l1, WritableByteChannel writableByteChannel ) throws IOException
    {
        return inner.transferTo( l, l1, writableByteChannel );
    }

    @Override
    public long transferFrom( ReadableByteChannel readableByteChannel, long l, long l1 ) throws IOException
    {
        fs.ensureHasSpace();
        return inner.transferFrom( readableByteChannel, l, l1 );
    }

    @Override
    public int read( ByteBuffer byteBuffer, long l ) throws IOException
    {
        return inner.read( byteBuffer, l );
    }

    @Override
    public int write( ByteBuffer byteBuffer, long l ) throws IOException
    {
        return inner.write( byteBuffer, l );
    }

    @Override
    public MappedByteBuffer map( MapMode mapMode, long l, long l1 ) throws IOException
    {
        return inner.map( mapMode, l, l1 );
    }

    @Override
    public FileLock lock( long l, long l1, boolean b ) throws IOException
    {
        return inner.lock( l, l1, b );
    }

    @Override
    public FileLock tryLock( long l, long l1, boolean b ) throws IOException
    {
        return inner.tryLock( l, l1, b );
    }

    @Override
    protected void implCloseChannel() throws IOException
    {
    }
}
