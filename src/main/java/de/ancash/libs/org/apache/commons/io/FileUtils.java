/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.ancash.libs.org.apache.commons.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Objects;

/**
 * General file manipulation utilities.
 * <p>
 * Facilities are provided in the following areas:
 * </p>
 * <ul>
 * <li>writing to a file
 * <li>reading from a file
 * <li>make a directory including parent directories
 * <li>copying files and directories
 * <li>deleting files and directories
 * <li>converting to and from a URL
 * <li>listing files and directories by filter and extension
 * <li>comparing file content
 * <li>file last changed date
 * <li>calculating a checksum
 * </ul>
 * <p>
 * Note that a specific charset should be specified whenever possible. Relying on the platform default means that the
 * code is Locale-dependent. Only use the default if the files are known to always use the platform default.
 * </p>
 * <p>
 * {@link SecurityException} are not documented in the Javadoc.
 * </p>
 * <p>
 * Origin of code: Excalibur, Alexandria, Commons-Utils
 * </p>
 */
public class FileUtils {
    /**
     * The number of bytes in a kilobyte.
     */
    public static final long ONE_KB = 1024;

    /**
     * The number of bytes in a kilobyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_KB_BI = BigInteger.valueOf(ONE_KB);

    /**
     * The number of bytes in a megabyte.
     */
    public static final long ONE_MB = ONE_KB * ONE_KB;

    /**
     * The number of bytes in a megabyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_MB_BI = ONE_KB_BI.multiply(ONE_KB_BI);

    /**
     * The number of bytes in a gigabyte.
     */
    public static final long ONE_GB = ONE_KB * ONE_MB;

    /**
     * The number of bytes in a gigabyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_GB_BI = ONE_KB_BI.multiply(ONE_MB_BI);

    /**
     * The number of bytes in a terabyte.
     */
    public static final long ONE_TB = ONE_KB * ONE_GB;

    /**
     * The number of bytes in a terabyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_TB_BI = ONE_KB_BI.multiply(ONE_GB_BI);

    /**
     * The number of bytes in a petabyte.
     */
    public static final long ONE_PB = ONE_KB * ONE_TB;

    /**
     * The number of bytes in a petabyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_PB_BI = ONE_KB_BI.multiply(ONE_TB_BI);

    /**
     * The number of bytes in an exabyte.
     */
    public static final long ONE_EB = ONE_KB * ONE_PB;

    /**
     * The number of bytes in an exabyte.
     *
     * @since 2.4
     */
    public static final BigInteger ONE_EB_BI = ONE_KB_BI.multiply(ONE_PB_BI);

    /**
     * The number of bytes in a zettabyte.
     */
    public static final BigInteger ONE_ZB = BigInteger.valueOf(ONE_KB).multiply(BigInteger.valueOf(ONE_EB));

    /**
     * The number of bytes in a yottabyte.
     */
    public static final BigInteger ONE_YB = ONE_KB_BI.multiply(ONE_ZB);

    /**
     * An empty array of type {@code File}.
     */
    public static final File[] EMPTY_FILE_ARRAY = {};

    /**
     * Opens a {@link FileOutputStream} for the specified file, checking and
     * creating the parent directory if it does not exist.
     * <p>
     * At the end of the method either the stream will be successfully opened,
     * or an exception will have been thrown.
     * </p>
     * <p>
     * The parent directory will be created if it does not exist.
     * The file will be created if it does not exist.
     * An exception is thrown if the file object exists but is a directory.
     * An exception is thrown if the file exists but cannot be written to.
     * An exception is thrown if the parent directory cannot be created.
     * </p>
     *
     * @param file the file to open for output, must not be {@code null}
     * @return a new {@link FileOutputStream} for the specified file
     * @throws NullPointerException if the file object is {@code null}.
     * @throws IllegalArgumentException if the file object is a directory
     * @throws IllegalArgumentException if the file is not writable.
     * @throws IOException if the directories could not be created.
     * @since 1.3
     */
    public static FileOutputStream openOutputStream(final File file) throws IOException {
        return openOutputStream(file, false);
    }

    /**
     * Opens a {@link FileOutputStream} for the specified file, checking and
     * creating the parent directory if it does not exist.
     * <p>
     * At the end of the method either the stream will be successfully opened,
     * or an exception will have been thrown.
     * </p>
     * <p>
     * The parent directory will be created if it does not exist.
     * The file will be created if it does not exist.
     * An exception is thrown if the file object exists but is a directory.
     * An exception is thrown if the file exists but cannot be written to.
     * An exception is thrown if the parent directory cannot be created.
     * </p>
     *
     * @param file   the file to open for output, must not be {@code null}
     * @param append if {@code true}, then bytes will be added to the
     *               end of the file rather than overwriting
     * @return a new {@link FileOutputStream} for the specified file
     * @throws NullPointerException if the file object is {@code null}.
     * @throws IllegalArgumentException if the file object is a directory
     * @throws IllegalArgumentException if the file is not writable.
     * @throws IOException if the directories could not be created.
     * @since 2.1
     */
    public static FileOutputStream openOutputStream(final File file, final boolean append) throws IOException {
        Objects.requireNonNull(file, "file");
        if (file.exists()) {
            requireFile(file, "file");
            requireCanWrite(file, "file");
        } else {
            createParentDirectories(file);
        }
        return new FileOutputStream(file, append);
    }
    
    /**
     * Copies bytes from an {@link InputStream} {@code source} to a file
     * {@code destination}. The directories up to {@code destination}
     * will be created if they don't already exist. {@code destination}
     * will be overwritten if it already exists.
     * <p>
     * <em>The {@code source} stream is closed.</em>
     * </p>
     * <p>
     * See {@link #copyToFile(InputStream, File)} for a method that does not close the input stream.
     * </p>
     *
     * @param source      the {@code InputStream} to copy bytes from, must not be {@code null}, will be closed
     * @param destination the non-directory {@code File} to write bytes to
     *                    (possibly overwriting), must not be {@code null}
     * @throws IOException if {@code destination} is a directory
     * @throws IOException if {@code destination} cannot be written
     * @throws IOException if {@code destination} needs creating but can't be
     * @throws IOException if an IO error occurs during copying
     * @since 2.0
     */
    public static void copyInputStreamToFile(final InputStream source, final File destination) throws IOException {
        try (InputStream inputStream = source) {
            copyToFile(inputStream, destination);
        }
    }
    
    /**
     * Copies bytes from an {@link InputStream} source to a {@link File} destination. The directories
     * up to {@code destination} will be created if they don't already exist. {@code destination} will be
     * overwritten if it already exists. The {@code source} stream is left open, e.g. for use with
     * {@link java.util.zip.ZipInputStream ZipInputStream}. See {@link #copyInputStreamToFile(InputStream, File)} for a
     * method that closes the input stream.
     *
     * @param inputStream the {@code InputStream} to copy bytes from, must not be {@code null}
     * @param file the non-directory {@code File} to write bytes to (possibly overwriting), must not be
     *        {@code null}
     * @throws NullPointerException if the InputStream is {@code null}.
     * @throws NullPointerException if the File is {@code null}.
     * @throws IllegalArgumentException if the file object is a directory.
     * @throws IllegalArgumentException if the file is not writable.
     * @throws IOException if the directories could not be created.
     * @throws IOException if an IO error occurs during copying.
     * @since 2.5
     */
    public static void copyToFile(final InputStream inputStream, final File file) throws IOException {
        try (OutputStream out = openOutputStream(file)) {
            IOUtils.copy(inputStream, out);
        }
    }
    
    /**
     * Requires that the given {@code File} is a file.
     *
     * @param file The {@code File} to check.
     * @param name The parameter name to use in the exception message.
     * @return the given file.
     * @throws NullPointerException if the given {@code File} is {@code null}.
     * @throws IllegalArgumentException if the given {@code File} does not exist or is not a directory.
     */
    private static File requireFile(final File file, final String name) {
        Objects.requireNonNull(file, name);
        if (!file.isFile()) {
            throw new IllegalArgumentException("Parameter '" + name + "' is not a file: " + file);
        }
        return file;
    }
    
    /**
     * Throws an {@link IllegalArgumentException} if the file is not writable. This provides a more precise exception
     * message than a plain access denied.
     *
     * @param file The file to test.
     * @param name The parameter name to use in the exception message.
     * @throws NullPointerException if the given {@code File} is {@code null}.
     * @throws IllegalArgumentException if the file is not writable.
     */
    private static void requireCanWrite(final File file, final String name) {
        Objects.requireNonNull(file, "file");
        if (!file.canWrite()) {
            throw new IllegalArgumentException("File parameter '" + name + " is not writable: '" + file + "'");
        }
    }
    
    /**
     * Creates all parent directories for a File object.
     *
     * @param file the File that may need parents, may be null.
     * @return The parent directory, or {@code null} if the given file does not name a parent
     * @throws IOException if the directory was not created along with all its parent directories.
     * @throws IOException if the given file object is not null and not a directory.
     * @since 2.9.0
     */
    public static File createParentDirectories(final File file) throws IOException {
        return mkdirs(getParentFile(file));
    }
    
    /**
     * Calls {@link File#mkdirs()} and throws an exception on failure.
     *
     * @param directory the receiver for {@code mkdirs()}, may be null.
     * @return the given file, may be null.
     * @throws IOException if the directory was not created along with all its parent directories.
     * @throws IOException if the given file object is not a directory.
     * @throws SecurityException See {@link File#mkdirs()}.
     * @see File#mkdirs()
     */
    private static File mkdirs(final File directory) throws IOException {
        if ((directory != null) && (!directory.mkdirs() && !directory.isDirectory())) {
            throw new IOException("Cannot create directory '" + directory + "'.");
        }
        return directory;
    }
    
    /**
     * Gets the parent of the given file. The given file may be bull and a file's parent may as well be null.
     *
     * @param file The file to query.
     * @return The parent file or {@code null}.
     */
    private static File getParentFile(final File file) {
        return file == null ? null : file.getParentFile();
    }
    
    /**
     * Instances should NOT be constructed in standard programming.
     * @deprecated Will be private in 3.0.
     */
    @Deprecated
    public FileUtils() { //NOSONAR

    }
}
