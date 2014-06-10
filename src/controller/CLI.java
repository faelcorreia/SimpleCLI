package controller;

import java.nio.CharBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class CLI {
	private Map<String, CLICommand> commands;

	public void addCommand(String command, String description, String usage) {
		if (commands == null) {
			commands = new HashMap<String, CLICommand>();
		}
		commands.put(command, new CLICommand(description, usage));
	}

	public String parse(String str) {
		if (str != null) {
			String[] strs = str.split(" ");
			if (commands.containsKey(strs[0])) {
				return strs[0];
			}
		}
		return null;
	}

	public String parseArgument(String str) {
		String[] strs = str.trim().split(" ", 2);
		if (strs.length == 2) {
			return strs[1];
		} else {
			return "";
		}
	}

	public String getUsage() {
		SortedSet<String> keys = new TreeSet<String>(commands.keySet());
		CLICommand helpCommand;
		String usage = "Help:";
		for (String key : keys) {
			helpCommand = commands.get(key);
			usage += "\n\n" + key + " - " + helpCommand.getDescription() + "\n";
			String spaces = CharBuffer.allocate(key.length() + 3).toString()
					.replace('\0', ' ');
			usage += spaces + "Usage: " + key + " " + helpCommand.getUsage();
		}
		return usage;
	}

	public String getUsage(String arg) {
		return "Usage: " + arg + " " + commands.get(arg).getUsage();
	}

	public class CLICommand {
		private String description;
		private String usage;

		public CLICommand() {

		}

		public CLICommand(String description, String usage) {
			this.description = description;
			this.usage = usage;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getUsage() {
			return usage;
		}

		public void setUsage(String usage) {
			this.usage = usage;
		}
	}
}
