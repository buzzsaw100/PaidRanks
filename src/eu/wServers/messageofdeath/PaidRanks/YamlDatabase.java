package eu.wServers.messageofdeath.PaidRanks;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class YamlDatabase {
	
	public JavaPlugin plugin = null;
	public String fileName = null, fileExtension = null;
	public File file = null;
	public FileConfiguration fileConfig = null;
	
	public YamlDatabase(JavaPlugin plugin, String fileName) {
		this.plugin = plugin;
		this.fileName = fileName;
		this.fileExtension = ".yml";
		fileConfig = new YamlConfiguration();
	}
	
	public void onStartUp() {
		file = new File(plugin.getDataFolder(), fileName + fileExtension);
		try{
			// *** Config ***
			if(!file.exists()){
				file.getParentFile().mkdirs();
				file.createNewFile();
				if(plugin.getResource(fileName + fileExtension) != null)
					copy(plugin.getResource(fileName + fileExtension), file);
			}
			fileConfig = new YamlConfiguration();
			fileConfig.load(file);
		}catch (Exception e){}
	}
	
	private void copy(InputStream in, File file) {
		try{
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0){
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void reload() {
		try{
			fileConfig.load(file);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public ConfigurationSection getConfigurationSection(String key, ConfigurationSection fallback) {
		if(fileConfig.contains(key)) {
			return fileConfig.getConfigurationSection(key);
		}else{
			return fallback;
		}
	}
	
	public ArrayList<String> getSection(String key) {
		if(fileConfig.contains(key)) {
			ArrayList<String> section = new ArrayList<String>();
			for(Object str : getConfigurationSection(key, null).getKeys(false).toArray()) {
				section.add(String.valueOf(str));
			}
			return section;
		}else{
			return null;
		}
	}

	/**
	 * Gets an integer value from the file.
	 * @param key
	 * @param fallback
	 * @return the integer for the key, if exists.
	 * @return fallback when the key doesn't exist.
	 */
	public int getInteger(String key, int fallback){
		if(fileConfig.contains(key)) {
			return fileConfig.getInt(key);
		}else{
			return fallback;
		}
	}

	/**
	 * Gets an string value from the file.
	 * @param key
	 * @param fallback
	 * @return the string for the key, if exists.
	 * @return fallback when the key doesn't exist.
	 */
	public String getString(String key, String fallback){
		if(fileConfig.contains(key)) {
			return fileConfig.getString(key);
		}else{
			return fallback;
		}
	}

	/**
	 * Gets an boolean value from the file. It will accept "true" and "false".
	 * @param key
	 * @param fallback
	 * @return the boolean for the key, if exists.
	 * @return fallback when the key doesn't exist.
	 */
	public boolean getBoolean(String key, boolean fallback){
		if(fileConfig.contains(key)) {
			return fileConfig.getBoolean(key);
		}else{
			return fallback;
		}
	}
	
	public ArrayList<String> getStringArray(String key, ArrayList<String> fallback) {
		if(fileConfig.contains(key)) {
			return (ArrayList<String>)fileConfig.getStringList(key);
		}else{
			return fallback;
		}
	}
 
	/**
	 * Gets an double value from the file.
	 * @param key
	 * @param fallback
	 * @return the double for the key, if exists.
	 * @return fallback when the key doesn't exist.
	 */
	public double getDouble(String key, double fallback){
		if(fileConfig.contains(key)) {
			return fileConfig.getDouble(key);
		}else{
			return fallback;
		}
	}

	/**
	 * Gets an float value from the file.
	 * @param key
	 * @param fallback
	 * @return the float for the key, if exists.
	 * @return fallback when the key doesn't exist.
	 */
	public float getFloat(String key, float fallback){
		if(fileConfig.contains(key)) {
			return (float) fileConfig.getDouble(key);
		}else{
			return fallback;
		}
	}

	/**
	 * Gets an material value from the file. It parses material-ids as well as Bukkit-Material names.
	 * @param key
	 * @param fallback
	 * @return the material for the key, if exists.
	 * @return fallback when the key doesn't exist.
	 */
	public Material getMaterial(String key, Material fallback){
		if(fileConfig.contains(key)) {
			return fileConfig.getItemStack(key).getType();
		}else{
			return fallback;
		}
	}

	/**
	 * When one key exists multiple times, use this method to get all values as strings in a list.
	 * @param key the key to search
	 * @return all values for that key.
	 */

	/**
	 * Writes the keyset to the file
	 */
	
	public void set(String key, Object set) {
		fileConfig.set(key, set);
		try {
			fileConfig.save(file);
		}catch(IOException e) {
		}
	}
}