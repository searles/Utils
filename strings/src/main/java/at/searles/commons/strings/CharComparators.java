package at.searles.commons.strings;

public enum CharComparators implements CharComparator {
	CASE_SENSITIVE {
		@Override
		public int compare(char ch0, char ch1) {
			return ch0 - ch1;
		}
	},
	CASE_INSENSITIVE {
		@Override
		public int compare(char ch0, char ch1) {
			ch0 = Character.toLowerCase(ch0);
			ch1 = Character.toLowerCase(ch0);
			
			if(ch0 != ch1) {
				// alphabetic order of non-numeric chars
			    return ch0 - ch1;
			}
			
			return 0;
		}
	}	
}
